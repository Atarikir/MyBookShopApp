package com.example.service;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final AuthService authService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    if (Arrays.stream(request.getCookies()).anyMatch(it -> it.getName().equals("token"))) {
      authService.saveTokenByBlackList(request);
    }

    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
      auth.setAuthenticated(false);
      SecurityContextHolder.clearContext();
      for (Cookie cookie : request.getCookies()) {
        String cookieName = cookie.getName();
        log.info("cookie name={}", cookieName);
        Cookie cookieToDelete = new Cookie(cookieName, null);
        cookieToDelete.setPath(request.getContextPath() + "/");
        cookieToDelete.setMaxAge(0);
        response.addCookie(cookieToDelete);
      }
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
}
