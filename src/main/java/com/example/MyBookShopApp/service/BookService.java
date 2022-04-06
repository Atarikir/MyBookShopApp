package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> getBooksData() {
        return bookRepository.findAll();
    }
}
