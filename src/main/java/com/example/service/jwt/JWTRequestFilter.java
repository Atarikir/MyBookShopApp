package com.example.service.jwt;

import com.example.data.BookStoreUserDetails;
import com.example.data.JwtBlackList;
import com.example.repository.JwtBlackListRepository;
import com.example.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
          username = jwtUtil.extractUsername(token);
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

  //    final String authHeader = httpServletRequest.getHeader("authorization");

//    if ("OPTIONS".equals(httpServletRequest.getMethod())) {
//      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//
//      filterChain.doFilter(httpServletRequest, httpServletResponse);
//    } else {
//
//      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//        throw new ServletException("Missing or invalid Authorization header");
//      }
//
//      token = authHeader.substring(7);
//      JwtBlackList blacklist = this.jwtBlackListRepository.findByTokenEquals(token);
//
//      if(blacklist == null) {
//        final Claims claims = Jwts.parser().setSigningKey("topsecretjwtpass".getBytes(
//            StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
//        httpServletRequest.setAttribute("claims", claims);
//      } else {
//        throw new ServletException("Invalid token." + "");
//
//      }
//      filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }

//  if (tokenProvider.validateToken(jwt) && userRepository.findOneWithAuthoritiesByEmailIgnoreCaseAndDeleteFlag(authentication.getName()).isPresent()) {
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//    LOG.debug("set Authentication to auth context for '{}', uri: {}", authentication.getName(), requestURI);
//    filterChain.doFilter(servletRequest, servletResponse);
//  }

  //check if the token is in the blacklist
//  if (StringUtils.hasText(jwt) && jwtTokenBlacklistRepository.findByToken(jwt).isPresent()) {
//    LOG.info("blacklisted token");
//    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//  }

//  /**
//   * Routinely Deletes expired tokens from the Blacklist
//   */
//  @Scheduled(cron = "0 0 12 * * ?")
//  public void deleteExpiredTokens() {
//    log.debug("Request to delete expired tokens from blacklist");
//    long now = (new Date().getTime()) / 1000;
//    jwtTokenBlacklistRepository.deleteAllByExpiryLessThan(now);
//  }
}
