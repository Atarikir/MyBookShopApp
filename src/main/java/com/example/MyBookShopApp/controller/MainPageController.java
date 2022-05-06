package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@Slf4j
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("all")
    public List<BookDto> allBooks() {
        log.info("all books");
        return bookService.getPageOfAllBooks(0,20);
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
