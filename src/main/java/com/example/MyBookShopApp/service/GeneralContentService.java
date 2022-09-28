package com.example.MyBookShopApp.service;

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
import org.springframework.ui.Model;

public class GeneralContentService {

  private final BookRepository bookRepository;
  private final BookMapper mapper;

  public GeneralContentService(BookRepository bookRepository, BookMapper mapper) {
    this.bookRepository = bookRepository;
    this.mapper = mapper;
  }

  public void getBookList(String contents, Model model, String nameCookieEmpty, String attribute) {
    if (contents == null || contents.equals("")) {
      model.addAttribute(nameCookieEmpty, true);
    } else {
      model.addAttribute(nameCookieEmpty, false);
      contents = contents.startsWith("/") ? contents.substring(1) : contents;
      contents =
          contents.endsWith("/") ? contents.substring(0, contents.length() - 1) :
              contents;
      String[] cookieSlugs = contents.split("/");
      List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
      List<BookDto> bookDtoList = mapper.listEntityToDtoList(booksFromCookieSlugs);
      model.addAttribute(attribute, bookDtoList);
    }
  }

  public void addCookie(String contents, String slug, HttpServletResponse response, Model model,
      String nameCookie, String nameCookieEmpty) {
    if (contents == null || contents.equals("")) {
      Cookie cookie = new Cookie(nameCookie, slug);
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute(nameCookieEmpty, false);
    } else if (!contents.contains(slug)) {
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add(contents).add(slug);
      Cookie cookie = new Cookie(nameCookie, stringJoiner.toString());
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute(nameCookieEmpty, false);
    }
  }

  public void removeCookie(String contents, String slug, HttpServletResponse response, Model model,
      String nameCookie, String nameCookieEmpty) {
    if (contents != null && !contents.equals("")) {
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(contents.split("/")));
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie(nameCookie, String.join("/", cookieBooks));
      cookie.setPath("/");
      response.addCookie(cookie);
      model.addAttribute(nameCookieEmpty, false);
    } else {
      model.addAttribute(nameCookieEmpty, true);
    }
  }
}
