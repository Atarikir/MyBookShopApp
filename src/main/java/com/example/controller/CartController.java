package com.example.controller;

import static com.example.data.enums.Book2UserType.CART;

import com.example.api.response.BookDto;
import com.example.service.GeneralContentService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class CartController extends BaseController {

  @ModelAttribute(name = "bookCart")
  public List<BookDto> bookCart() {
    return new ArrayList<>();
  }

  //private static final String CART_EMPTY = "isCartEmpty";
  //private static final String CART_CONTENTS = "cartContents";
  private final GeneralContentService generalContentService;

  @GetMapping("/cart")
  public String handleCartRequest(Model model, HttpServletRequest request) {
    int totalPrice = 0;
    int totalDiscountPrice = 0;
    List<BookDto> bookList = generalContentService.getBookListByStatus(request, CART);
    if (bookList.isEmpty()) {
      model.addAttribute("isCartEmpty", true);
    } else {
      for (BookDto book : bookList) {
        totalPrice = totalPrice + book.getPrice();
        totalDiscountPrice = totalDiscountPrice + book.getDiscountPrice();
      }
      model.addAttribute("bookCart", bookList);
      model.addAttribute("totalPrice", totalPrice);
      model.addAttribute("totalDiscountPrice", totalDiscountPrice);
    }
    return "cart";
  }
//
//  @PostMapping("/changeBookStatus/cart/{slug}")
//  public String handleChangeBookStatus(@PathVariable("slug") String slug,
//      @CookieValue(name = "cartContents", required = false) String cartContents,
//      HttpServletResponse response, Model model) {
//    cartService.addCookie(cartContents, slug, response, model, CART_CONTENTS, CART_EMPTY);
//    return "redirect:/books/" + slug;
//  }
//
//  @PostMapping("/changeBookStatus/cart/remove/{slug}")
//  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
//      @CookieValue(name =
//          "cartContents", required = false) String cartContents, HttpServletResponse response,
//      Model model) {
//    cartService.removeCookie(cartContents, slug, response, model, CART_CONTENTS, CART_EMPTY);
//    return "redirect:/cart";
//  }
}
