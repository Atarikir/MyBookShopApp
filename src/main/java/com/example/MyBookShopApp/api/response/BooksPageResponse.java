package com.example.MyBookShopApp.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BooksPageResponse {
    private Integer count;
    private List<BookDto> books;
}
