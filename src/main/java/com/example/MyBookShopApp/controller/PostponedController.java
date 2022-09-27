package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostponedController {

  private final BookRepository bookRepository;
  private final BookMapper mapper;

  public PostponedController(BookRepository bookRepository, BookMapper mapper) {
    this.bookRepository = bookRepository;
    this.mapper = mapper;
  }

  @ModelAttribute("bookPostponed")
  public List<BookEntity> bookPostponed() {
    return new ArrayList<>();
  }

  @GetMapping("/postponed")
  public String handlePostponedRequest(
      @CookieValue(value = "postponedContents", required = false) String postponedContents,
      Model model) {
    if (postponedContents == null || postponedContents.equals("")) {
      model.addAttribute("isPostponedEmpty", true);
    } else {
      model.addAttribute("isPostponedEmpty", false);
      postponedContents =
          postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
      postponedContents =
          postponedContents.endsWith("/") ? postponedContents.substring(0,
              postponedContents.length() - 1) :
              postponedContents;
      String[] cookieSlugs = postponedContents.split("/");
      List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
      List<BookDto> bookDtoList = mapper.listEntityToDtoList(booksFromCookieSlugs);
      model.addAttribute("bookPostponed", bookDtoList);
    }
    return "postponed";
  }

  @PostMapping("/changeBookStatus/postponed/{slug}")
  public String handleChangeBookStatus(@PathVariable("slug") String slug,
      @CookieValue(name = "postponedContents", required = false) String postponedContents,
      HttpServletResponse response, Model model) {
    if (postponedContents == null || postponedContents.equals("")) {
      Cookie cookie = new Cookie("postponedContents", slug);
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isPostponedEmpty", false);
    } else if (!postponedContents.contains(slug)) {
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add(postponedContents).add(slug);
      Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isPostponedEmpty", false);
    }
    return "redirect:/books/" + slug;
  }

  @PostMapping("/changeBookStatus/postponed/remove/{slug}")
  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
      @CookieValue(name =
          "postponedContents", required = false) String postponedContents,
      HttpServletResponse response,
      Model model) {
    if (postponedContents != null && !postponedContents.equals("")) {
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isPostponedEmpty", false);
    } else {
      model.addAttribute("isPostponedEmpty", true);
    }

    return "redirect:/postponed";
  }
}
