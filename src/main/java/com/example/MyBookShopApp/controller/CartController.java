package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

  @ModelAttribute(name = "bookCart")
  public List<BookDto> bookCart() {
    return new ArrayList<>();
  }

  private final BookRepository bookRepository;
  private final BookMapper mapper;

  public CartController(BookRepository bookRepository, BookMapper mapper) {
    this.bookRepository = bookRepository;
    this.mapper = mapper;
  }

  @GetMapping("/cart")
  public String handleCartRequest(
      @CookieValue(value = "cartContents", required = false) String cartContents,
      Model model) {
    if (cartContents == null || cartContents.equals("")) {
      model.addAttribute("isCartEmpty", true);
    } else {
      model.addAttribute("isCartEmpty", false);
      cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
      cartContents =
          cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) :
              cartContents;
      String[] cookieSlugs = cartContents.split("/");
      List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
      List<BookDto> bookDtoList = mapper.listEntityToDtoList(booksFromCookieSlugs);
      model.addAttribute("bookCart", bookDtoList);
    }
    return "cart";
  }

  @PostMapping("/changeBookStatus/cart/{slug}")
  public String handleChangeBookStatus(@PathVariable("slug") String slug,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      HttpServletResponse response, Model model) {
    if (cartContents == null || cartContents.equals("")) {
      Cookie cookie = new Cookie("cartContents", slug);
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    } else if (!cartContents.contains(slug)) {
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add(cartContents).add(slug);
      Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    }
    return "redirect:/books/" + slug;
  }

  @PostMapping("/changeBookStatus/cart/remove/{slug}")
  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
      @CookieValue(name =
          "cartContents", required = false) String cartContents, HttpServletResponse response,
      Model model) {
    if (cartContents != null && !cartContents.equals("")) {
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    } else {
      model.addAttribute("isCartEmpty", true);
    }
    return "redirect:/cart";
  }
}
