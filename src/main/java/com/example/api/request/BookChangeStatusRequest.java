package com.example.api.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookChangeStatusRequest {

  private String status;
  private Integer booksIds;
}
