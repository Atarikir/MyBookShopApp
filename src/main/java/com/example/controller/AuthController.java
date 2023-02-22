package com.example.controller;

import com.example.api.request.RegistrationForm;
import com.example.service.UserRegisterService;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
    try {
      userRegisterService.registerNewUser(registrationForm);
      model.addAttribute("regOk", true);
    } catch (EntityExistsException ex) {
      model.addAttribute("regNotOk", true);
    }

    return "signin";
  }

  @GetMapping("/my")
  public String handleMy(Model model) {
    model.addAttribute("currentUser", userRegisterService.getCurrentUser());
    return "my";
  }

  @GetMapping("/myarchive")
  public String handleMyArchive(Model model) {
    model.addAttribute("curUser", userRegisterService.getCurrentUser());
    return "myarchive";
  }

  @GetMapping("/profile")
  public String handleProfile(Model model) {
    model.addAttribute("curUsr", userRegisterService.getCurrentUser());
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
//      cookie.setMaxAge(0);
//    }
//    return "redirect:/";
//  }
}