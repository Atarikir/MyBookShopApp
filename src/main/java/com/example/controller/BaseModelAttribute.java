package com.example.controller;

import static com.example.data.enums.Book2UserType.CART;
import static com.example.data.enums.Book2UserType.KEPT;

import com.example.api.response.BookDto;
import com.example.data.enums.Book2UserType;
import com.example.service.GeneralContentService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseModelAttribute {

  private final GeneralContentService generalContentService;

  @ModelAttribute("cartSize")
  public Integer getCartSize(HttpServletRequest request) {
    return this.bookListSize(CART, request);
  }

  @ModelAttribute("postponedSize")
  public Integer getPostponedSize(HttpServletRequest request) {
    return this.bookListSize(KEPT, request);
  }

  private Integer bookListSize(Book2UserType type, HttpServletRequest request) {
    List<BookDto> userBooks = generalContentService.getBookListByStatus(request, type);
    return userBooks.isEmpty() ? 0 : userBooks.size();
  }
}
