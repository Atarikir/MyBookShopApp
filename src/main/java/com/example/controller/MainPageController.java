package com.example.controller;

import com.example.service.BookService;
import com.example.service.TagService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainPageController extends BaseController {

  @Value("${value.offset}")
  private int offset;
  @Value("${value.limit}")
  private int limit;
  private final BookService bookService;
  private final TagService tagService;

  @GetMapping("/")
  public String mainPage(@RequestParam(value = "from", required = false) String from,
      @RequestParam(value = "to", required = false) String to,
      Model model, HttpServletRequest request) {
    model.addAttribute("recommended", bookService.getRecommendedBooksList(offset, limit, request));
    model.addAttribute("recent", bookService.getRecentBooksList(from, to, offset, limit, request));
    model.addAttribute("popular", bookService.getPopularBooksList(offset, limit, request));
    model.addAttribute("tags", tagService.getAllTags());
    return "index";
  }
}
