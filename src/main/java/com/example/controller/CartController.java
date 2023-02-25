package com.example.controller;

import com.example.api.response.BookDto;
import com.example.service.CartService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CartController extends BaseController {

  @ModelAttribute(name = "bookCart")
  public List<BookDto> bookCart() {
    return new ArrayList<>();
  }

  private static final String CART_EMPTY = "isCartEmpty";
  private static final String CART_CONTENTS = "cartContents";
  private final CartService cartService;

  @GetMapping("/cart")
  public String handleCartRequest(
      @CookieValue(value = "cartContents", required = false) String cartContents,
      Model model) {
    cartService.getBookList(cartContents, model, CART_EMPTY, "bookCart");
    return "cart";
  }

  @PostMapping("/changeBookStatus/cart/{slug}")
  public String handleChangeBookStatus(@PathVariable("slug") String slug,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      HttpServletResponse response, Model model) {
    cartService.addCookie(cartContents, slug, response, model, CART_CONTENTS, CART_EMPTY);
    return "redirect:/books/" + slug;
  }

  @PostMapping("/changeBookStatus/cart/remove/{slug}")
  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
      @CookieValue(name =
          "cartContents", required = false) String cartContents, HttpServletResponse response,
      Model model) {
    cartService.removeCookie(cartContents, slug, response, model, CART_CONTENTS, CART_EMPTY);
    return "redirect:/cart";
  }
}
