package com.example.MyBookShopApp.api.response;

import com.example.MyBookShopApp.data.book.BookEntity;
import lombok.Data;
import org.springframework.data.domain.Page;

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
