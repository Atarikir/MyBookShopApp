package com.example.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BooksListPageResponse {

  private Long count;
  private List<BookResponseDto> books;
}
