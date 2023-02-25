package com.example.service;

import static java.util.Objects.nonNull;

import com.example.api.request.ContactConfirmationPayload;
import com.example.api.request.RegistrationForm;
import com.example.api.response.ContactConfirmationResponse;
import com.example.api.response.ResultErrorResponse;
import com.example.data.BookStoreUserDetails;
import com.example.data.user.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.jwt.JWTUtil;
import java.util.Arrays;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final UtilityService utilityService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public void registerNewUser(RegistrationForm registrationForm) {
    UserEntity userEntity = userRepository.findByEmail(registrationForm.getEmail());
    if (userEntity == null) {
      UserEntity user = new UserEntity();
      user.setRegTime(utilityService.getTimeNow());
      user.setName(registrationForm.getName());
      user.setEmail(registrationForm.getEmail());
      user.setPhone(registrationForm.getPhone());
      user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
      userRepository.save(user);
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
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(oAuth2User.getAttribute("sub")));
    user.setName((String) oAuth2User.getAttributes().get("name"));
    user.setRegTime(utilityService.getTimeNow());
    return new BookStoreUserDetails(user).user();
  }

  @Transactional
  public void saveOAuth2User() {
    userRepository.save(this.getOAuth2User());
  }

  public ResultErrorResponse login(ContactConfirmationPayload payload) {
    return utilityService.getResultTrue();
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
    if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("sub"))) {
      email = this.getOAuth2User().getEmail();
    }
    if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("token"))) {
      email = this.getCurrentUser().getEmail();
    }
    return userRepository.findByEmail(email);
  }
}
