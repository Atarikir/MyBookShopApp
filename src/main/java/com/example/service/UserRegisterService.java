package com.example.service;

import static java.util.Objects.nonNull;

import com.example.api.request.ContactConfirmationPayload;
import com.example.api.request.RegistrationForm;
import com.example.api.response.ContactConfirmationResponse;
import com.example.data.BookStoreUserDetails;
import com.example.data.book.links.Book2UserEntity;
import com.example.data.user.UserEntity;
import com.example.repository.Book2UserRepository;
import com.example.repository.UserRepository;
import com.example.service.jwt.JWTUtil;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegisterService {

  @Value("${lengthHash}")
  private int lengthHash;

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final BookUtilService bookUtilService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final Book2UserRepository book2UserRepository;

  @Transactional
  public void registerNewUser(RegistrationForm registrationForm, HttpServletRequest request,
      HttpServletResponse response) {
    UserEntity userEntity = userRepository.findByEmail(registrationForm.getEmail());
    if (userEntity == null) {
      UserEntity userAnon = this.getAnonymousUser(request);
      userAnon.setRegTime(UtilityService.getTimeNowUTC());
      userAnon.setName(registrationForm.getName());
      userAnon.setEmail(registrationForm.getEmail());
      userAnon.setPhone(registrationForm.getPhone());
      userAnon.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
      userRepository.save(userAnon);
      UtilityService.removeCookie("username",
          UtilityService.getCookieValueByName(request, "username"),
          UtilityService.getCookieValueByName(request, "username"), response);
    } else {
      throw new EntityExistsException("Такой email уже существует. Введите другой адрес почты.");
    }
  }

  public UserEntity getOAuth2User() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String email = (String) oAuth2User.getAttributes().get("email");
    UserEntity user = userRepository.findByEmail(email);
    if (nonNull(user)) {
      return new BookStoreUserDetails(user).user();
    }
    user = new UserEntity();
    user.setHash(RandomStringUtils.randomAlphanumeric(lengthHash));
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(oAuth2User.getAttribute("sub")));
    user.setName((String) oAuth2User.getAttributes().get("name"));
    user.setRegTime(UtilityService.getTimeNowUTC());
    return new BookStoreUserDetails(user).user();
  }

  @Transactional
  public void saveOAuth2User() {
    userRepository.save(this.getOAuth2User());
  }

  @Transactional
  public ContactConfirmationResponse login(ContactConfirmationPayload payload,
      HttpServletRequest request, HttpServletResponse response) {
    ContactConfirmationResponse loginResponse = this.jwtLogin(payload);
    Cookie cookie = new Cookie("token", loginResponse.getResult());
    response.addCookie(cookie);
    UserEntity userAnon = this.getAnonymousUser(request);
    if (!userAnon.getBookEntityList().isEmpty()) {
      UserEntity user = userRepository.findByEmail(payload.getContact());
      List<Book2UserEntity> book2UserList = book2UserRepository.findByUserId(userAnon.getId());
      book2UserList.forEach(book2UserAnon -> {
            Book2UserEntity book2User = Book2UserEntity.builder()
                .time(UtilityService.getTimeNowUTC())
                .typeId(book2UserAnon.getTypeId())
                .userId(user.getId())
                .bookId(book2UserAnon.getBookId())
                .build();
            Book2UserEntity book2UserDb = book2UserRepository.findByBookIdAndUserId(
                book2User.getBookId(), book2User.getUserId());
            if (book2UserDb == null) {
              book2UserRepository.save(book2User);
            }
            book2UserRepository.delete(book2UserAnon);
            bookUtilService.bookUpdateWhenPopularityChanges(book2UserAnon.getBookId());
          }
      );
    }
    return loginResponse;
  }

  public UserEntity getCurrentUser() {
    BookStoreUserDetails userDetails =
        (BookStoreUserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
    return userDetails.user();
  }

  public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
        payload.getCode()));
    BookStoreUserDetails userDetails =
        (BookStoreUserDetails) userService.loadUserByUsername(payload.getContact());
    String jwtToken = jwtUtil.generateToken(userDetails);
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult(jwtToken);
    return response;
  }

  public UserEntity getRegisteredUser(HttpServletRequest request) {
    String email = null;
    if (request.getCookies() != null) {
      if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("sub"))) {
        email = this.getOAuth2User().getEmail();
      }
      if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("token"))) {
        email = this.getCurrentUser().getEmail();
      }
    }
    if (email != null) {
      return userRepository.findByEmail(email);
    } else {
      return null;
    }
  }

  public UserEntity getAnonymousUser(HttpServletRequest request) {
    String userName = UtilityService.getCookieValueByName(request, "username");
    if (userName != null && !userName.isEmpty()) {
      return userRepository.findByEmail(userName);
    }
    return null;
  }

  public UserEntity getUser(HttpServletRequest request) {
    UserEntity user;
    user = this.getRegisteredUser(request);
    if (user == null) {
      user = this.getAnonymousUser(request);
    }
    return user;
  }
}
