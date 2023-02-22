package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
public class DocumentsController extends BaseController {

  @GetMapping
  public String documentsPage() {
    return "documents/index";
  }

  @GetMapping("/SLUG")
  public String documentSlugPage() {
    return "documents/slug";
  }
}
