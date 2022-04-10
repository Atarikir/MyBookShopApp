package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("all")
    public List<BookDto> allBooks() {
        return bookService.getPageOfAllBooks(0,20);
    }

    @ModelAttribute("recommended")
    public List<BookDto> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 20);
    }

    @ModelAttribute("recent")
    public List<BookDto> recentBooks() {
        return bookService.getPageOfRecentBooks(0, 20);
    }

    @ModelAttribute("popular")
    public List<BookDto> popularBooks() {
        return bookService.getPageOfPopularBooks(0, 20);
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
