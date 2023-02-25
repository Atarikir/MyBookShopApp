package com.example.controller.api;

import com.example.api.request.ContactConfirmationPayload;
import com.example.api.response.ContactConfirmationResponse;
import com.example.service.AuthService;
import com.example.service.UserRegisterService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

  private final AuthService authService;
  private final UserRegisterService userRegisterService;

  @PostMapping("/requestContactConfirmation")
  public ContactConfirmationResponse handleRequestContactConfirmation(
      @RequestBody ContactConfirmationPayload payload) {
    return authService.contactConfirmation();
  }

  @PostMapping("/approveContact")
  public ContactConfirmationResponse handleApproveContact(
      @RequestBody ContactConfirmationPayload payload) {
    return authService.contactConfirmation();
  }

  @PostMapping("/login")
  public ContactConfirmationResponse handleLogin(
      @RequestBody ContactConfirmationPayload payload, HttpServletResponse httpServletResponse) {
    ContactConfirmationResponse loginResponse = userRegisterService.jwtLogin(payload);
    Cookie cookie = new Cookie("token", loginResponse.getResult());
    httpServletResponse.addCookie(cookie);
    return loginResponse;
  }
}
