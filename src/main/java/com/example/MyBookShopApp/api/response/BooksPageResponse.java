package com.example.MyBookShopApp.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BooksPageResponse {
    private Integer count;
    private List<BookDto> books;
}
