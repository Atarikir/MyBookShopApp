package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class MyBookShopAppTest extends BaseTest{

  @Value("${auth.secret}")
  String authSecret;

  private final MyBookShopApp app;

  @Autowired
  MyBookShopAppTest(MyBookShopApp app) {
    this.app = app;
  }

  //@Test
  void contextLoads() {
    assertNotNull(app);
  }

  //@Test
  void verifyAuthSecret() {
    assertThat(authSecret, Matchers.containsString("box"));
  }
}
