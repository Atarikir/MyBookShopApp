package com.example.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
class MainPageControllerTest extends BaseTest {

  private final MockMvc mockMvc;

  @Autowired
  MainPageControllerTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  //@Test
  void mainPageAccessTest() throws Exception {
    mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(content().string(containsString("")))
        .andExpect(status().isOk());
  }

  //@Test
  void accessOnlyAuthorizedPageFailTest() throws Exception {
    mockMvc.perform(get("/my"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/signin"));
  }

  //@Test
  void correctLoginTest() throws Exception {
    mockMvc.perform(formLogin("/signin").user("admin@bk.ru").password("123456"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
  }

//  @Test
//  @WithUserDetails("admin@bk.ru")
//  void authenticatedAccessToProfilePageTest() throws Exception {
//    mockMvc.perform(get("/profile"))
//        .andDo(print())
//        .andExpect(authenticated())
//        .andExpect(xpath("/html/body/header/div[1]/div/div/div[3]/div/a[4]/span[1]")
//            .string("DIK"));
//  }

//  @Test
//  void searchQueryTest() throws Exception {
//    mockMvc.perform(get("/search/Sudden"))
//        .andDo(print())
//        .andExpect(xpath("/html/body/div/div/main/div[2]/div/div[1]/div[2]/strong/a")
//            .string("Sudden Manhattan"));
//
//  }
}