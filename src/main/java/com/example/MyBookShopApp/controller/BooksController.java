package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/recommended")
    public ResponseEntity<BooksPageResponse> recommendedBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                              @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfRecommendedBooks(offset, limit));
    }

    @GetMapping("/books/recent")
    public ResponseEntity<BooksPageResponse> recentBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                             @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfRecentBooks(offset, limit));
    }

    @GetMapping("/books/popular")
    public ResponseEntity<BooksPageResponse> popularBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                              @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfPopularBooks(offset, limit));
    }
}
