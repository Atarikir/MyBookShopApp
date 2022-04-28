package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookEntity> getBooksData() {
        return bookRepository.findAll();
    }

    public List<BookDto> getPageOfAllBooks(Integer offset, Integer limit) {
        return getBookDtoList(getAllBooks(offset, limit));
    }

    private Page<BookEntity> getAllBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(getPageable(offset, limit));
    }

    public List<BookDto> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        return getBookDtoList(getRecommendedBooks(offset, limit));
    }

    private Page<BookEntity> getRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(getPageable(offset, limit));
    }

    public List<BookDto> getPageOfRecentBooks(Integer offset, Integer limit) {
        return getBookDtoList(getRecentBooks(offset, limit));
    }

    private Page<BookEntity> getRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(getPageable(offset, limit));
    }

    public List<BookDto> getPageOfPopularBooks(Integer offset, Integer limit) {
        return getBookDtoList(getPopularBooks(offset, limit));
    }

    private Page<BookEntity> getPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(getPageable(offset, limit));
    }

    private List<BookDto> getBookDtoList(Page<BookEntity> books) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity book : books) {
            bookDtoList.add(bookMapper.bookEntityToBookDTO(book));
        }
        return bookDtoList;
    }

    private Pageable getPageable(Integer offset, Integer limit) {
        return PageRequest.of(offset, limit);
    }
}
