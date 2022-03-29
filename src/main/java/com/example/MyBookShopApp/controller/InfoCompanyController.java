package com.example.MyBookShopApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoCompanyController {

    @GetMapping("/about")
    public String InfoCompanyPage() {
        return "about";
    }
}
