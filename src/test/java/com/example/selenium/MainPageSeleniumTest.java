package com.example.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.BaseTest;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

class MainPageSeleniumTest extends BaseTest {

  private static ChromeDriver driver;

  @BeforeAll
  static void setup() {
    System.setProperty("webdriver.chrome.driver",
        "/home/shelby/IdeaProjects/Skillbox/Spring_framework/BookShop/chromedriver_linux64/chromedriver");
    driver = new ChromeDriver();
    driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
  }

  @AfterAll
  static void tearDown() {
    driver.quit();
  }

  //@Test
  void testMainPageAccess() throws InterruptedException {
    MainPage mainPage = new MainPage(driver);
    mainPage
        .callPage()
        .pause();

    assertTrue(driver.getPageSource().contains("BOOKSHOP"));
  }


}