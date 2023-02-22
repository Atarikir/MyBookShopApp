package com.example.service;

import com.example.api.response.ContactConfirmationResponse;
import com.example.data.JwtBlackList;
import com.example.repository.JwtBlackListRepository;
import com.example.service.jwt.JWTUtil;
import io.jsonwebtoken.JwtException;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final JWTUtil jwtUtil;
  private final JwtBlackListRepository jwtBlackListRepository;

  public ContactConfirmationResponse contactConfirmation() {
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult("true");
    return response;
  }

  @Transactional
  public void saveTokenByBlackList(HttpServletRequest httpServletRequest) {
    String token = null;
    Cookie[] cookies = httpServletRequest.getCookies();
    try {
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals("token")) {
            token = cookie.getValue();
            log.debug("token - " + token);
//          username = jwtUtil.extractUsername(token);
          }
        }

        Date dateTokenExpiration = jwtUtil.extractExpiration(token);
        JwtBlackList jwtBlackList = new JwtBlackList(token, dateTokenExpiration);
        Date now = new Date(System.currentTimeMillis());
        jwtBlackListRepository.deleteJwtFromBlackList(now);
        jwtBlackListRepository.save(jwtBlackList);
      }
    } catch (JwtException ex)  {
      HttpSession session = httpServletRequest.getSession();
      SecurityContextHolder.clearContext();
      if (session != null) {
        session.invalidate();
      }
    }
  }
}