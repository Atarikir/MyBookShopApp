package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BooksListPageResponse;
import com.example.MyBookShopApp.service.AuthorService;
import com.example.MyBookShopApp.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final AuthorService authorService;

  @Autowired
  public BooksController(BookService bookService, AuthorService authorService) {
    this.bookService = bookService;
    this.authorService = authorService;
  }

  @GetMapping(value = "/books/recommended", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<BooksListPageResponse> recommendedBooksPage(
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit) {
    return ResponseEntity.ok(bookService.getPageOfRecommendedBooks(offset, limit));
  }

  @GetMapping(value = "/books/recent", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<BooksListPageResponse> recentBooksPage(
      @RequestParam(value = "from", required = false) String from,
      @RequestParam(value = "to", required = false) String to,
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit) {
    return ResponseEntity.ok(bookService.getPageOfRecentBooks(from, to, offset, limit));
  }

  @GetMapping(value = "/books/popular", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<BooksListPageResponse> popularBooksPage(
      @RequestParam(value = "offset", required = false) Integer offset,
      @RequestParam(value = "limit", required = false) Integer limit) {
    return ResponseEntity.ok(bookService.getPageOfPopularBooks(offset, limit));
  }

  @GetMapping(value = "/books/recent", produces = MediaType.TEXT_HTML_VALUE)
  public String recentPage(
      @RequestParam(value = "from", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusMonths(1).format(T(java.time.format.DateTimeFormatter).ofPattern('dd.MM.yyyy'))}")
      String from,
      @RequestParam(value = "to", required = false, defaultValue = "#{T(java.time.LocalDate).now().format(T(java.time.format.DateTimeFormatter).ofPattern('dd.MM.yyyy'))}")
      String to,
      Model model) {
    model.addAttribute("recent", bookService.getRecentBooksList(from, to, offset, limit));
    return "books/recent";
  }

  @GetMapping(value = "/books/popular", produces = MediaType.TEXT_HTML_VALUE)
  public String popularPage(Model model) {
    model.addAttribute("popular", bookService.getPopularBooksList(offset, limit));
    return "books/popular";
  }

  //TODO: сделать как в recent from-to (в порядке убывания даты публикации — от самой новой до самой старой)
  @GetMapping("/books/author/{slug}")
  public String getBooksAuthorSlugPage(@PathVariable("slug") String slug, Model model) {
    model.addAttribute("authorBySlug", authorService.getAuthorDtoBySlug(slug));
    model.addAttribute("booksByAuthorSlug",
        bookService.getBooksByAuthorSlugList(slug, offset, limit));
    return "books/author";
  }

  @GetMapping("/books/{slug}")
  public String getBookPageBySlug(@PathVariable("slug") String slug, Model model) {
    model.addAttribute("bookBySlug", bookService.getBookDtoBySlug(slug));
    model.addAttribute("authorsByBook", authorService.getAuthorByBookSlug(slug));
    return "books/slug";
  }
}
