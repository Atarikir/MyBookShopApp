package com.example.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {

  @ModelAttribute
  public void addAttributes(Model model, HttpServletRequest request) {
    if (request.getCookies() != null) {
      model.addAttribute("isAuth", Arrays.stream(request.getCookies()).anyMatch(
              it -> it.getName().equals("token")
          )
      );
    } else {
      model.addAttribute("isNotAuth", true);
    }
  }
}
