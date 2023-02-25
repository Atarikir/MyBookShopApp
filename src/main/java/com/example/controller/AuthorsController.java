package com.example.controller;

import com.example.data.author.AuthorEntity;
import com.example.service.AuthorService;
import com.example.service.BookService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AuthorsController extends BaseController {

  @Value("${value.offset}")
  private int offset;
  @Value("${value.limit}")
  private int limit;
  private final BookService bookService;
  private final AuthorService authorService;

  @ModelAttribute("authorsMap")
  public Map<String, List<AuthorEntity>> authorsMap() {
    return authorService.getAuthorsMap();
  }

  @GetMapping("/authors")
  public String authorsPage() {
    return "authors/index";
  }

  @ApiOperation("method to get map of authors")
  @GetMapping("/api/authors")
  @ResponseBody
  public Map<String, List<AuthorEntity>> authors() {
    return authorService.getAuthorsMap();
  }

  //TODO: сделать как в recent from-to, некорректно выводит список книг автора
  @GetMapping("/authors/{slug}")
  public String getAuthorPageBySlug(@PathVariable("slug") String slug, Model model) {
    model.addAttribute("authorBySlug", authorService.getAuthorDtoBySlug(slug));
    model.addAttribute("booksByAuthorSlug",
        bookService.getBooksByAuthorSlugList(slug, offset, limit));
    return "authors/slug";
  }
}
