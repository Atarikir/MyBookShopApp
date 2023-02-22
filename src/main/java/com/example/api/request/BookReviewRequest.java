package com.example.api.request;

import lombok.Data;

@Data
public class BookReviewRequest {

  private Integer bookId;
  private String text;
}
