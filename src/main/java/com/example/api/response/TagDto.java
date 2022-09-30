package com.example.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagDto {

  private int id;
  private String name;
  private String slug;
  private List<BookResponseDto> bookList;
}
