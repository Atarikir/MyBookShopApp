package com.example.controller;

import com.example.service.BookService;
import com.example.service.GenreService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class GenresController extends BaseController {

  @Value("${value.offset}")
  private int offset;
  @Value("${value.limit}")
  private int limit;
  private final GenreService genreService;
  private final BookService bookService;

  @GetMapping("/genres")
  public String genresPage(Model model) {
    model.addAttribute("genres", genreService.getGenreParentIdNullList());
    return "genres/index";
  }

  @GetMapping(value = "/genres/{slug}")
  public String getGenreSlugPage(@PathVariable("slug") String slug, Model model,
      HttpServletRequest request) {
    model.addAttribute("genreBySlug", genreService.getGenreDtoBySlug(slug));
    model.addAttribute("booksByGenreSlug",
        bookService.getBooksByGenreSlugList(slug, offset, limit, request));
    return "genres/slug";
  }
}
