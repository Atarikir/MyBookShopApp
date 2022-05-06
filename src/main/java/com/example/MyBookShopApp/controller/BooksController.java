package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.api.response.SearchWordDto;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

//        @ModelAttribute("recommended")
//    public List<BookDto> recommendedBooks() {
//        log.info("recommended");
//        return bookService.getRecommendedBooks(0, 20);
//    }
//
//    @ModelAttribute("recent")
//    public List<BookDto> recentBooks() {
//        log.info("recent");
//        return bookService.getPageOfRecentBooks(0, 20);
//    }
//
//    @ModelAttribute("popular")
//    public List<BookDto> popularBooks() {
//        log.info("popular");
//        return bookService.getPageOfPopularBooks(0, 20);
//    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    //TODO: переделать на List<BookDto>
    @ModelAttribute("searchResults")
    public List<BookEntity> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public ResponseEntity<BooksPageResponse> recommendedBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                                                  @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfRecommendedBooks(offset, limit));
    }

    @GetMapping(value = "/books/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BooksPageResponse> recentBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                                             @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfRecentBooks(offset, limit));
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public ResponseEntity<BooksPageResponse> popularBooksPage(@RequestParam(value = "offset", required = false) Integer offset,
                                                              @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfPopularBooks(offset, limit));
    }

    @GetMapping(value = "/books/recent", produces = MediaType.TEXT_HTML_VALUE)
    public String recentPage() {
        return "books/recent";
    }

    @GetMapping(value = "/books/popular", produces = MediaType.TEXT_HTML_VALUE)
    public String popularPage() {
        return "books/popular";
    }

//    @GetMapping(value = {"/search", "/search/{searchWord}"}, produces = MediaType.TEXT_HTML_VALUE)
//    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
//                                   Model model) {
//        model.addAttribute("searchWordDto", searchWordDto);
//        model.addAttribute("searchResults",
//                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 20));
//        return "/search/index";
//    }
//
//    @GetMapping(value = "/search/{searchWord}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseEntity<BooksPageResponse> getNextSearchNextPage(@PathVariable(value = "searchWord", required = false)
//                                                   SearchWordDto searchWordDto,
//                                                   @RequestParam(value = "offset", required = false) Integer offset,
//                                                   @RequestParam(value = "limit", required = false) Integer limit) {
//        return ResponseEntity.ok(bookService.getPageOfSearchResultsBooks(searchWordDto.getExample(), offset, limit));
//    }
}
