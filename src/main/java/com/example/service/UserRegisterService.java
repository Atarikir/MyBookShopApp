package com.example.service;

import com.example.api.request.ContactConfirmationPayload;
import com.example.api.request.RegistrationForm;
import com.example.api.response.ContactConfirmationResponse;
import com.example.api.response.ResultErrorResponse;
import com.example.data.BookStoreUserDetails;
import com.example.data.user.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.jwt.JWTUtil;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

  private final JWTUtil jwtUtil;
  private final UserService userService;
  private final UtilityService utilityService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

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

  public ResultErrorResponse login(ContactConfirmationPayload payload) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//    OAuth2User user = oauthToken.getPrincipal();
//    Authentication authentication =
//        authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(payload.getContact(),
//                payload.getCode()));
//    SecurityContextHolder.getContext().setAuthentication(authentication);
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

  public UserEntity getRegisteredUser() {
    return userRepository.findByEmail(this.getCurrentUser().getEmail());
  }
}
