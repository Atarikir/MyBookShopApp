package com.example.controller;

import com.example.api.request.RegistrationForm;
import com.example.service.UserRegisterService;
import java.util.Arrays;
import javax.persistence.EntityExistsException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController extends BaseController {

  private final UserRegisterService userRegisterService;

  @GetMapping("/signin")
  public String authPage() {
    return "signin";
  }

  @GetMapping("/signup")
  public String handleSignUp(Model model) {
    model.addAttribute("regForm", new RegistrationForm());
    return "signup";
  }

  @PostMapping("/reg")
  public String handleUserRegistration(RegistrationForm registrationForm, Model model,
      HttpServletRequest request, HttpServletResponse response) {
    try {
      userRegisterService.registerNewUser(registrationForm, request, response);
      model.addAttribute("regOk", true);
    } catch (EntityExistsException ex) {
      model.addAttribute("regNotOk", true);
    }

    return "signin";
  }

  @GetMapping("/my")
  public String handleMy(Model model, HttpServletRequest request) {
    if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("sub"))) {
      model.addAttribute("currentUser", userRegisterService.getOAuth2User());
    } else {
      model.addAttribute("currentUser", userRegisterService.getCurrentUser());
    }
    return "my";
  }

  @GetMapping("/oauth2LoginSuccess")
  public String getOauth2LoginInfo(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
      HttpServletResponse response) {
    Cookie cookie = new Cookie("sub", oAuth2User.getAttribute("sub"));
    response.addCookie(cookie);
    model.addAttribute("currentUser", userRegisterService.getOAuth2User());
    userRegisterService.saveOAuth2User();
    return "my";

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
  }

  @GetMapping("/myarchive")
  public String handleMyArchive(Model model, HttpServletRequest request) {
    if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("sub"))) {
      model.addAttribute("curUser", userRegisterService.getOAuth2User());
    } else {
      model.addAttribute("curUser", userRegisterService.getCurrentUser());
    }
    return "myarchive";
  }

  @GetMapping("/profile")
  public String handleProfile(Model model, HttpServletRequest request) {
    if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("sub"))) {
      model.addAttribute("curUsr", userRegisterService.getOAuth2User());
    } else {
      model.addAttribute("curUsr", userRegisterService.getCurrentUser());
    }
    return "profile";
  }

//  @GetMapping("/logout")
//  public String handleLogout(HttpServletRequest request) {
//    HttpSession session = request.getSession();
//    SecurityContextHolder.clearContext();
//    if (session != null) {
//      session.invalidate();
//    }
//    for (Cookie cookie : request.getCookies()) {
//      cookie.setValue("");
//      cookie.setPath("/");
//      cookie.setMaxAge(0);
//    }
//    return "redirect:/";
//  }
}