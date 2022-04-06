package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("booksList")
    public List<BookEntity> bookList() {
        return bookService.getBooksData();
    }

    @GetMapping("/books/popular")
    public String popularBookPage() {
        return "books/popular";
    }

    @GetMapping("/books/recent")
    public String recentBooksPage() {
        return "books/recent";
    }
}
