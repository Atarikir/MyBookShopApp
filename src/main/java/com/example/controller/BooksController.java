package com.example.controller;

import com.example.data.book.review.BookReviewEntity;
import com.example.service.AuthorService;
import com.example.service.BookReviewService;
import com.example.service.BookService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

  @Value("${value.offset}")
  private int offset;
  @Value("${value.limit}")
  private int limit;
  private final BookService bookService;
  private final AuthorService authorService;
  private final BookReviewService bookReviewService;

  @GetMapping(value = "/recent", produces = MediaType.TEXT_HTML_VALUE)
  public String recentPage(
      @RequestParam(value = "from", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusMonths(1).format(T(java.time.format.DateTimeFormatter).ofPattern('dd.MM.yyyy'))}")
      String from,
      @RequestParam(value = "to", required = false, defaultValue = "#{T(java.time.LocalDate).now().format(T(java.time.format.DateTimeFormatter).ofPattern('dd.MM.yyyy'))}")
      String to,
      Model model) {
    model.addAttribute("recent", bookService.getRecentBooksList(from, to, offset, limit));
    return "books/recent";
  }

  @GetMapping(value = "/popular", produces = MediaType.TEXT_HTML_VALUE)
  public String popularPage(Model model) {
    model.addAttribute("popular", bookService.getPopularBooksList(offset, limit));
    return "books/popular";
  }

  //TODO: сделать как в recent from-to (в порядке убывания даты публикации — от самой новой до самой старой)
  @GetMapping("/author/{slug}")
  public String getBooksAuthorSlugPage(@PathVariable("slug") String slug, Model model) {
    model.addAttribute("authorBySlug", authorService.getAuthorDtoBySlug(slug));
    model.addAttribute("booksByAuthorSlug",
        bookService.getBooksByAuthorSlugList(slug, offset, limit));
    return "books/author";
  }

  @GetMapping("/{slug}")
  public String getBookPageBySlug(@PathVariable("slug") String slug, Model model) {
//    log.info("slug in BookController - " + slug);
    model.addAttribute("bookBySlug", bookService.getBookDtoBySlug(slug));
    model.addAttribute("authorsByBook", authorService.getAuthorListByBookSlug(slug));
    model.addAttribute("bookReviewList", bookReviewService.getBookReviewListByBookSlug(slug));
//    model.addAttribute("likes", bookReviewService.getCountLikes(review));
//    model.addAttribute("dislikes", bookReviewService.getCountDislikes(review));
    return "books/slug";
  }

  @PostMapping("/{slug}/img/save")
  public String saveNewBookImage(@RequestParam("file") MultipartFile file,
      @PathVariable("slug") String slug, Model model) throws IOException {
    model.addAttribute("bookBySlug", bookService.getBookDtoBySlug(slug));
    //TODO:slug приходит null
//    log.info("file - " + file + " slug in BookController- " + slug);
    bookService.saveImageBook(file, slug);
    return "redirect:/books/" + slug;
  }
}
