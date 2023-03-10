package com.example.interceptor;

import com.example.data.user.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.UtilityService;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class UserLoginInterceptor implements HandlerInterceptor {

  @Value("${lengthHash}")
  private int lengthHash;

  private final UserRepository userRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      Object principal = authentication.getPrincipal();
      if (principal instanceof UserEntity) {
        return HandlerInterceptor.super.preHandle(request, response, handler);
      }
      if (principal.equals("anonymousUser")) {
        String userName = UtilityService.getCookieValueByName(request, "username");
        if (ObjectUtils.isEmpty(userName)) {
          userName = String.join("-", "user", UUID.randomUUID().toString());
          if (userRepository.findByEmail(userName) == null) {
            userRepository.save(UserEntity.builder()
                .hash(RandomStringUtils.randomAlphanumeric(lengthHash))
                .regTime(UtilityService.getTimeNowUTC())
                .email(userName)
                .name(authentication.getName())
                .build());
          }
          UtilityService.addCookieToHttpResponse("username", userName, response);
        }
      }
    }
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}