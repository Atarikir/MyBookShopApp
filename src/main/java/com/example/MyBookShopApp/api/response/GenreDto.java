package com.example.MyBookShopApp.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenreDto {

  private int id;
  private Integer parentId;
  private String slug;
  private String name;
  private List<GenreDto> subGenres;
  private List<BookResponseDto> bookList;
}
