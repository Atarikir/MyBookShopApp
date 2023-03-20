package com.example.service;

import com.example.api.response.ResultErrorResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UtilityService {

  public static ResultErrorResponse getResultTrue() {
    return ResultErrorResponse.builder()
        .result(true)
        .build();
  }

  public static ResultErrorResponse getResultFalse() {
    return ResultErrorResponse.builder()
        .result(false)
        .build();
  }

  public static ResultErrorResponse errorsResponse(String errorText) {
    return ResultErrorResponse.builder()
        .result(false)
        .error(errorText)
        .build();
  }

  public static LocalDateTime getTimeNowUTC() {
    return LocalDateTime.now(ZoneId.of("UTC"));
  }

  public static void addCookieToHttpResponse(String nameCookie, String valueCookie,
      HttpServletResponse response) {
    Cookie cookie = new Cookie(nameCookie, valueCookie);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  public static String getCookieValueByName(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    String cookieValue = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(cookieName)) {
          cookieValue = cookie.getValue();
        }
      }
    }
    return cookieValue;
  }

  public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response) {
    for (Cookie cookie : request.getCookies()) {
      String cookieName = cookie.getName();
      log.info("cookie name={}", cookieName);
      Cookie cookieToDelete = new Cookie(cookieName, null);
      cookieToDelete.setPath(request.getContextPath() + "/");
      cookieToDelete.setMaxAge(0);
      response.addCookie(cookieToDelete);
    }
  }

  public static void removeCookie(String nameCookie, String valueCookie,
      String cookieValueDelimiter, HttpServletResponse response) {
    ArrayList<String> cookie = new ArrayList<>(Arrays.asList(valueCookie.split("/")));
    cookie.remove(cookieValueDelimiter);
    addCookieToHttpResponse(nameCookie, String.join("/", cookie), response);
  }
}
