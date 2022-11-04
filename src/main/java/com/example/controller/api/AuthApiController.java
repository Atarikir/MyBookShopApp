package com.example.controller.api;

import com.example.api.request.ContactConfirmationPayload;
import com.example.api.response.ContactConfirmationResponse;
import com.example.service.AuthService;
import com.example.service.UserRegisterService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

//  @PostMapping("/oauth2LoginSuccess")
//  public String getOauth2LoginInfo() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//    OAuth2User user = oauthToken.getPrincipal();
//    // fetching the client details and user details
//    System.out.println(authenticationToken.getAuthorizedClientRegistrationId()); // client name like facebook, google etc.
//    System.out.println(authenticationToken.getName()); // facebook/google userId
//
//    //		1.Fetching User Info
//    OAuth2User user = authenticationToken.getPrincipal(); // When you login with OAuth it gives you OAuth2User else UserDetails
//    System.out.println("userId: "+user.getName()); // returns the userId of facebook something like 12312312313212
//    // getAttributes map Contains User details like name, email etc// print the whole map for more details
//    System.out.println("Email:"+ user.getAttributes().get("email"));
//
//    //2. Just in case if you want to obtain User's auth token value, refresh token, expiry date etc you can use below snippet
//    OAuth2AuthorizedClient client = userRegisterService.login()
//        .loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(),
//            authenticationToken.getName());
//    System.out.println("Token Value"+ client.getAccessToken().getTokenValue());
//  }
}
