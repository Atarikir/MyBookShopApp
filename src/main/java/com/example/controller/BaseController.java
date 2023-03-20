package com.example.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class BaseController {

  @ModelAttribute
  public void addAttributes(Model model, HttpServletRequest request) {
    if (request.getCookies() != null) {
      if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("sub"))
          || Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("token"))) {
        model.addAttribute("isAuth", true);
      } else {
        model.addAttribute("isNotAuth", true);
      }
    }
  }
}
