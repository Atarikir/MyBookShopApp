package com.example.MyBookShopApp.controller;

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

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    //TODO: Переделать на List<BookDto>
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

//    @GetMapping(value = {"/search", "/search/{searchWord}"})
//    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
//                                   Model model) {
//        model.addAttribute("searchWordDto", searchWordDto);
//        model.addAttribute("searchResults",
//                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 20));
//        return "/search/index";
//    }
//
//    @GetMapping("/search/page/{searchWord}")
//    @ResponseBody
//    public ResponseEntity<BooksPageResponse> getNextSearchNextPage(@PathVariable(value = "searchWord", required = false)
//                                                   SearchWordDto searchWordDto,
//                                                   @RequestParam(value = "offset", required = false) Integer offset,
//                                                   @RequestParam(value = "limit", required = false) Integer limit) {
//        return ResponseEntity.ok(bookService.getPageOfSearchResultsBooks(searchWordDto.getExample(), offset, limit));
//    }
}
