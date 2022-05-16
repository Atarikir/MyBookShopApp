package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class MainPageController {

    @Value("${value.offset}")
    private int offset;
    @Value("${value.limit}")
    private int limit;
    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String mainPage(@RequestParam(value = "from", required = false) String from,
                           @RequestParam(value = "to", required = false) String to,
                           Model model) {
        model.addAttribute("recommended", bookService.getRecommendedBooksList(offset, limit));
        model.addAttribute("recent", bookService.getRecentBooksList(from, to, offset, limit));
        model.addAttribute("popular", bookService.getPopularBooksList(offset, limit));
        return "index";
    }
}
