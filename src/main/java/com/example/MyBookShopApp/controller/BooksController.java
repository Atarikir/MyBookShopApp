package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class BooksController {

    @Value("${value.offset}")
    private int offset;
    @Value("${value.limit}")
    private int limit;
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books/recommended", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BooksPageResponse> recommendedBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                                                  @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfRecommendedBooks(offset, limit));
    }

    @GetMapping(value = "/books/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BooksPageResponse> recentBooksPage(@RequestParam(value = "from", required = false) String from,
                                                             @RequestParam(value = "to", required = false) String to,
                                                             @RequestParam(value = "offset", required = false) Integer offset,
                                                             @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfRecentBooks(from, to, offset, limit));
    }

    @GetMapping(value = "/books/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BooksPageResponse> popularBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                                              @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfPopularBooks(offset, limit));
    }

    @GetMapping(value = "/books/recent", produces = MediaType.TEXT_HTML_VALUE)
    public String recentPage(@RequestParam(value = "from", required = false) String from,
                             @RequestParam(value = "to", required = false) String to,
                             Model model) {
        model.addAttribute("recent", bookService.getRecentBooksList(from, to, offset, limit));
        return "books/recent";
    }

    @GetMapping(value = "/books/popular", produces = MediaType.TEXT_HTML_VALUE)
    public String popularPage(Model model) {
        model.addAttribute("popular", bookService.getPopularBooksList(offset, limit));
        return "books/popular";
    }
}
