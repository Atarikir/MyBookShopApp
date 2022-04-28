package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/recommended")
    public BooksPageResponse recommendedBooksPage(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit) {
        return new BooksPageResponse(bookService.getPageOfRecommendedBooks(offset, limit));
    }

    @GetMapping("/books/recent")
    public BooksPageResponse recentBooksPage(@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        return new BooksPageResponse(bookService.getPageOfRecentBooks(offset, limit));
    }

    @GetMapping("/books/popular")
    public BooksPageResponse popularBooksPage(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit) {
        return new BooksPageResponse(bookService.getPageOfPopularBooks(offset, limit));
    }
}
