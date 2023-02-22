package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController extends BaseController {

  @GetMapping("/contacts")
  public String contactsPage() {
    return "contacts";
  }
}
