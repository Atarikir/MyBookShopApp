package com.example.service;

import static com.example.data.enums.Book2UserType.CART;
import static com.example.data.enums.Book2UserType.KEPT;

import com.example.api.request.BookChangeStatusRequest;
import com.example.api.response.BookDto;
import com.example.api.response.ResultErrorResponse;
import com.example.data.book.BookEntity;
import com.example.data.book.links.Book2UserEntity;
import com.example.data.enums.Book2UserType;
import com.example.data.user.UserEntity;
import com.example.mapper.BookMapper;
import com.example.repository.Book2UserRepository;
import com.example.repository.BookRepository;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralContentService {

  private final UserRegisterService userRegisterService;
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final Book2UserRepository book2UserRepository;

  public List<BookDto> getBookListByStatus(HttpServletRequest request, Book2UserType type) {
    if (request.getCookies() != null) {
      List<BookEntity> userBooks;
      UserEntity user = userRegisterService.getUser(request);
      userBooks = bookRepository.getUserBooksByStatus(user.getId(),
          Book2UserType.getBindingTypeId(type));
      return bookMapper.listEntityToDtoList(userBooks);
    }
    return Collections.emptyList();
  }

  @Transactional
  public ResultErrorResponse changeBookStatus(BookChangeStatusRequest request,
      HttpServletRequest httpServletRequest) {
    UserEntity user = userRegisterService.getUser(httpServletRequest);
    Book2UserEntity book2User = this.book2User(user, request);
    if (request.getStatus().equals("UNLINK")) {
      this.deleteBook2User(book2User);
      //this.popularityCalculation();
    }
    if (request.getStatus().equals("CART")) {
      this.checkingBookBindingToUser(book2User);
      this.saveBook2User(CART, user, request);
      //this.popularityCalculation();
    }
    if (request.getStatus().equals("KEPT")) {
      this.checkingBookBindingToUser(book2User);
      this.saveBook2User(KEPT, user, request);
      //this.popularityCalculation();
    }
    return UtilityService.getResultTrue();
  }

  //TODO: реализовать расчёт популярности книги -
  //Популярность книги представляет собой неотрицательное число, которое
  //можно рассчитать по следующей формуле:
  //P = B + 0,7*C + 0,4*K,
  //где B — количество пользователей, купивших книгу, C — количество
  //пользователей, у которых книга находится в корзине, а K — количество
  //пользователей, у которых книга отложена.
  public void popularityCalculation(BookChangeStatusRequest request) {

  }

  private void checkingBookBindingToUser(Book2UserEntity book2User) {
    if (book2User != null) {
      this.deleteBook2User(book2User);
    }
  }

  private Book2UserEntity book2User(UserEntity user, BookChangeStatusRequest request) {
    return book2UserRepository.findByBookIdAndUserId(request.getBooksIds(), user.getId());
  }

  private void deleteBook2User(Book2UserEntity book2User) {
    book2UserRepository.delete(book2User);
  }

  private void saveBook2User(Book2UserType type, UserEntity user,
      BookChangeStatusRequest request) {
    Book2UserEntity book2User = Book2UserEntity.builder()
        .typeId(Book2UserType.getBindingTypeId(type))
        .bookId(request.getBooksIds())
        .userId(user.getId())
        .time(UtilityService.getTimeNowUTC())
        .build();
    book2UserRepository.save(book2User);
  }

//  public void getBookList(String contents, Model model, String nameCookieEmpty, String attribute) {
//    if (contents == null || contents.equals("")) {
//      model.addAttribute(nameCookieEmpty, true);
//    } else {
//      model.addAttribute(nameCookieEmpty, false);
//      contents = contents.startsWith("/") ? contents.substring(1) : contents;
//      contents =
//          contents.endsWith("/") ? contents.substring(0, contents.length() - 1) :
//              contents;
//      String[] cookieSlugs = contents.split("/");
//      List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
//      List<BookDto> bookDtoList = mapper.listEntityToDtoList(booksFromCookieSlugs);
//      model.addAttribute(attribute, bookDtoList);
//    }
//  }
//
//  public void addCookie(String contents, String slug, HttpServletResponse response, Model model,
//      String nameCookie, String nameCookieEmpty) {
//    if (contents == null || contents.equals("")) {
//      UtilityService.addCookieToHttpResponse(nameCookie, slug, response);
////      Cookie cookie = new Cookie(nameCookie, slug);
////      cookie.setPath("/");
////      response.addCookie(cookie);
//      model.addAttribute(nameCookieEmpty, false);
//    } else if (!contents.contains(slug)) {
//      StringJoiner stringJoiner = new StringJoiner("/");
//      stringJoiner.add(contents).add(slug);
//      UtilityService.addCookieToHttpResponse(nameCookie, stringJoiner.toString(), response);
////      Cookie cookie = new Cookie(nameCookie, stringJoiner.toString());
////      cookie.setPath("/");
////      response.addCookie(cookie);
//      model.addAttribute(nameCookieEmpty, false);
//    }
//  }
//
//  public void removeCookie(String contents, String slug, HttpServletResponse response, Model model,
//      String nameCookie, String nameCookieEmpty) {
//    if (contents != null && !contents.equals("")) {
//      UtilityService.removeCookie(nameCookie, contents, slug, response);
////      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(contents.split("/")));
////      cookieBooks.remove(slug);
////      UtilityService.addCookieToHttpResponse(nameCookie, String.join("/", cookieBooks), response);
////      Cookie cookie = new Cookie(nameCookie, String.join("/", cookieBooks));
////      cookie.setPath("/");
////      response.addCookie(cookie);
//      model.addAttribute(nameCookieEmpty, false);
//    } else {
//      model.addAttribute(nameCookieEmpty, true);
//    }
//  }
}
