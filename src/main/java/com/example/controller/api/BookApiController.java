package com.example.controller.api;

import com.example.api.response.BooksListPageResponse;
import com.example.service.BookService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApiController {

  private final BookService bookService;

  @GetMapping(value = "/recommended", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BooksListPageResponse> recommendedBooksPage(
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request) {
    return ResponseEntity.ok(bookService.getPageOfRecommendedBooks(offset, limit, request));
  }

  @GetMapping(value = "/recent", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BooksListPageResponse> recentBooksPage(
      @RequestParam(value = "from", required = false) String from,
      @RequestParam(value = "to", required = false) String to,
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request) {
    return ResponseEntity.ok(bookService.getPageOfRecentBooks(from, to, offset, limit, request));
  }

  @GetMapping(value = "/popular", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BooksListPageResponse> popularBooksPage(
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request) {
    return ResponseEntity.ok(bookService.getPageOfPopularBooks(offset, limit, request));
  }

  @GetMapping("/tag/{id}")
  public ResponseEntity<BooksListPageResponse> getBooksByTagId(
      @PathVariable(value = "id", required = false) Integer id,
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request) {
    return ResponseEntity.ok(bookService.getPageOfBooksByTagId(id, offset, limit, request));
  }

//  @GetMapping("/author/{id}")
//  @ResponseBody
//  public ResponseEntity<BooksListPageResponse> getBooksByAuthorId(
//      @PathVariable(value = "id", required = false) Integer id,
//      @RequestParam(value = "offset", required = false) Integer offset,
//      @RequestParam(value = "limit", required = false) Integer limit) {
//    return ResponseEntity.ok(bookService.getPageOfBooksByAuthorId(id, offset, limit));
//  }

  @GetMapping("/genre/{id}")
  public ResponseEntity<BooksListPageResponse> getBooksByGenreId(
      @PathVariable(value = "id", required = false) Integer id,
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request) {
    return ResponseEntity.ok(bookService.getPageOfBooksByGenreId(id, offset, limit, request));
  }
}
