package com.example.selenium;

import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {

  private String url = "http://localhost:8085/";
  private ChromeDriver driver;

  public MainPage(ChromeDriver driver) {
    this.driver = driver;
  }

  public MainPage callPage() {
    driver.get(url);
    return this;
  }

  public MainPage pause() throws InterruptedException {
    Thread.sleep(2000);
    return this;
  }
}
