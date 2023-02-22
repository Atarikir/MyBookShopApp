package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoCompanyController extends BaseController {

  @GetMapping("/about")
  public String InfoCompanyPage() {
    return "about";
  }
}
