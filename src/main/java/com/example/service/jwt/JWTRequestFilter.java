package com.example.service.jwt;

import com.example.data.BookStoreUserDetails;
import com.example.data.JwtBlackList;
import com.example.repository.JwtBlackListRepository;
import com.example.service.UserService;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final JWTUtil jwtUtil;
  private final JwtBlackListRepository jwtBlackListRepository;

//  //ask spring to inject the values based on current context
//  @PostConstruct
//  public void init() {
//    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String username = null;
    Cookie[] cookies = httpServletRequest.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")) {
          token = cookie.getValue();
          try {
            username = jwtUtil.extractUsername(token);
          } catch (JwtException exception) {
            HttpSession session = httpServletRequest.getSession();
            SecurityContextHolder.clearContext();
            if (session != null) {
              session.invalidate();
            }
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);

            httpServletResponse.addCookie(cookie);
          }
        }
      }

      JwtBlackList blacklist = jwtBlackListRepository.findJwtBlackListByToken(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
          && blacklist == null) {
        BookStoreUserDetails userDetails = (BookStoreUserDetails) userService.loadUserByUsername(
            username);
        if (Boolean.TRUE.equals(jwtUtil.validateToken(token, userDetails))) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
