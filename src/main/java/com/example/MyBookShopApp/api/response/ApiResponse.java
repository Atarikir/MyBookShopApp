package com.example.MyBookShopApp.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {

  private HttpStatus status;

  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timeStamp;

  private String message;
  private String debugMessage;
  private Collection<T> data;

  public ApiResponse() {
    this.timeStamp = LocalDateTime.now();
  }

  public ApiResponse(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }
}
