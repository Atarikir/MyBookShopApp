package com.example.service;

import com.example.api.response.ContactConfirmationResponse;
import com.example.data.JwtBlackList;
import com.example.data.book.links.Book2UserEntity;
import com.example.data.user.UserEntity;
import com.example.repository.JwtBlackListRepository;
import com.example.service.jwt.JWTUtil;
import io.jsonwebtoken.JwtException;
import java.util.Date;
import java.util.List;
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
  public void saveTokenByBlackList(HttpServletRequest request) {
    String token = null;
//    Cookie[] cookies = httpServletRequest.getCookies();
    try {
      if (request.getCookies() != null) {
        token = UtilityService.getCookieValueByName(request,"token");
//        for (Cookie cookie : cookies) {
//          if (cookie.getName().equals("token")) {
//            token = cookie.getValue();
//            log.debug("token - " + token);
//          }
//        }

        Date dateTokenExpiration = jwtUtil.extractExpiration(token);
        JwtBlackList jwtBlackList = new JwtBlackList(token, dateTokenExpiration);
        Date now = new Date(System.currentTimeMillis());
        jwtBlackListRepository.deleteJwtFromBlackList(now);
        jwtBlackListRepository.save(jwtBlackList);
      }
    } catch (JwtException ex)  {
      HttpSession session = request.getSession();
      SecurityContextHolder.clearContext();
      if (session != null) {
        session.invalidate();
      }
    }
  }
}