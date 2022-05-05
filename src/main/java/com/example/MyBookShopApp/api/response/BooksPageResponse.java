package com.example.MyBookShopApp.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksPageResponse {
    private Integer count;
    private List<BookDto> books;
}
