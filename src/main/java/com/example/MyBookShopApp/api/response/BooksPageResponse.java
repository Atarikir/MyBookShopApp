package com.example.MyBookShopApp.api.response;

import lombok.Data;

import java.util.List;

@Data
public class BooksPageResponse {

    private Integer count;
    private List<BookDto> books;

    public BooksPageResponse(List<BookDto> books) {
        this.books = books;
        this.count = books.size();
    }
}
