package com.example.controller;

import com.example.data.book.BookEntity;
import com.example.service.PostponedService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostponedController {

  private final PostponedService postponedService;
  private static final String POSTPONED_EMPTY = "isPostponedEmpty";
  private static final String POSTPONED_CONTENTS = "postponedContents";

  public PostponedController(PostponedService postponedService) {
    this.postponedService = postponedService;
  }

  @ModelAttribute("bookPostponed")
  public List<BookEntity> bookPostponed() {
    return new ArrayList<>();
  }

  @GetMapping("/postponed")
  public String handlePostponedRequest(
      @CookieValue(value = "postponedContents", required = false) String postponedContents,
      Model model) {
    postponedService.getBookList(postponedContents, model, POSTPONED_EMPTY, "bookPostponed");
    return "postponed";
  }

  @PostMapping("/changeBookStatus/postponed/{slug}")
  public String handleChangeBookStatus(@PathVariable("slug") String slug,
      @CookieValue(name = "postponedContents", required = false) String postponedContents,
      HttpServletResponse response, Model model) {
    postponedService.addCookie(postponedContents, slug, response, model, POSTPONED_CONTENTS,
        POSTPONED_EMPTY);
    return "redirect:/books/" + slug;
  }

  @PostMapping("/changeBookStatus/postponed/remove/{slug}")
  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
      @CookieValue(name =
          "postponedContents", required = false) String postponedContents,
      HttpServletResponse response,
      Model model) {
    postponedService.removeCookie(postponedContents, slug, response, model, POSTPONED_CONTENTS,
        POSTPONED_EMPTY);
    return "redirect:/postponed";
  }
}
