package com.example.api.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

  private int id;
  private String photo;
  private String slug;
  private String name;
  private String description;
  private List<BookResponseDto> bookList;
}
