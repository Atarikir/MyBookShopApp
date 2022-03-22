package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.repository.BookJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    private final BookJdbcRepository bookJdbcRepository;

    @Autowired
    public BookService(BookJdbcRepository bookJdbcRepository) {
        this.bookJdbcRepository = bookJdbcRepository;
    }

    public List<Book> getBooksData() {
        List<Book> books = bookJdbcRepository.getAll();
        return new ArrayList<>(books);
    }
}
