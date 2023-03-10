package com.example.controller.api;

import com.example.api.request.ContactConfirmationPayload;
import com.example.api.response.ContactConfirmationResponse;
import com.example.data.book.links.Book2UserEntity;
import com.example.data.user.UserEntity;
import com.example.repository.Book2UserRepository;
import com.example.repository.UserRepository;
import com.example.service.AuthService;
import com.example.service.UserRegisterService;
import com.example.service.UtilityService;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
      @RequestBody ContactConfirmationPayload payload, HttpServletResponse response,
      HttpServletRequest request) {
    return userRegisterService.login(payload, request, response);
  }
}