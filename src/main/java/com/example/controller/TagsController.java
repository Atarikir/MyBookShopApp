package com.example.controller;

import com.example.service.BookService;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagsController extends BaseController {

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
