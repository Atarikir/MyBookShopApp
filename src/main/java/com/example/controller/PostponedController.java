package com.example.controller;

import static com.example.data.enums.Book2UserType.KEPT;

import com.example.api.response.BookDto;
import com.example.data.book.BookEntity;
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
public class PostponedController extends BaseController {

  @ModelAttribute("bookPostponed")
  public List<BookEntity> bookPostponed() {
    return new ArrayList<>();
  }

  private final GeneralContentService generalContentService;
//  private static final String POSTPONED_EMPTY = "isPostponedEmpty";
//  private static final String POSTPONED_CONTENTS = "postponedContents";

  @GetMapping("/postponed")
  public String handlePostponedRequest(Model model, HttpServletRequest request) {
    List<BookDto> bookList = generalContentService.getBookListByStatus(request, KEPT);
    if (bookList.isEmpty()) {
      model.addAttribute("isPostponedEmpty", true);
    } else {
      model.addAttribute("bookPostponed", bookList);
    }
    return "postponed";
  }

//  @GetMapping("/postponed")
//  public String handlePostponedRequest(
//      @CookieValue(value = "postponedContents", required = false) String postponedContents,
//      Model model) {
//    postponedService.getBookList(postponedContents, model, POSTPONED_EMPTY, "bookPostponed");
//    return "postponed";
//  }

//  @PostMapping("/changeBookStatus/postponed/{slug}")
//  public String handleChangeBookStatus(@PathVariable("slug") String slug,
//      @CookieValue(name = "postponedContents", required = false) String postponedContents,
//      HttpServletResponse response, Model model) {
//    postponedService.addCookie(postponedContents, slug, response, model, POSTPONED_CONTENTS,
//        POSTPONED_EMPTY);
//    return "redirect:/books/" + slug;
//  }
//
//  @PostMapping("/changeBookStatus/postponed/remove/{slug}")
//  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
//      @CookieValue(name =
//          "postponedContents", required = false) String postponedContents,
//      HttpServletResponse response,
//      Model model) {
//    postponedService.removeCookie(postponedContents, slug, response, model, POSTPONED_CONTENTS,
//        POSTPONED_EMPTY);
//    return "redirect:/postponed";
//  }
}
