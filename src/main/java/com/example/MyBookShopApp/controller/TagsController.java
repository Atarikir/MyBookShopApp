package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BooksListPageResponse;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagsController {

  @Value("${value.offset}")
  private int offset;
  @Value("${value.limit}")
  private int limit;
  private final BookService bookService;
  private final TagService tagService;


  public TagsController(BookService bookService, TagService tagService) {
    this.bookService = bookService;
    this.tagService = tagService;
  }

  @GetMapping(value = "/tags/{slug}")
  public String getTagSlugPage(@PathVariable("slug") String slug, Model model) {
    model.addAttribute("tagBySlug", tagService.getTagDtoBySlug(slug));
    model.addAttribute("booksByTagSlug", bookService.getBooksByTagSlugList(slug, offset, limit));
    return "tags/index";
  }
}
