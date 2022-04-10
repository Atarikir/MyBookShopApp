package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserTypeEntity;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
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

    public List<BookDto> getPageOfAllBooks(Integer offset, Integer limit) {
        return getBookDtoList(getAllBooks(offset, limit));
    }

    public List<BookDto> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        return getBookDtoList(getRecommendedBooks(offset, limit));
    }

    public List<BookDto> getPageOfRecentBooks(Integer offset, Integer limit) {
        return getBookDtoList(getRecentBooks(offset, limit));
    }

    public List<BookDto> getPageOfPopularBooks(Integer offset, Integer limit) {
        return getBookDtoList(getPopularBooks(offset, limit));
    }

    private List<BookDto> getBookDtoList(List<BookEntity> books) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity book : books) {
            bookDtoList.add(createBookDto(book));
        }
        return bookDtoList;
    }

    private List<BookEntity> getAllBooks(Integer offset, Integer limit) {
        return bookRepository
                .findAll(getPageable(offset, limit))
                .getContent();
    }

    private List<BookEntity> getRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository
                .findAll(getPageable(offset, limit))
                .getContent();
    }

    private List<BookEntity> getRecentBooks(Integer offset, Integer limit) {
        return bookRepository
                .findAll(getPageable(offset, limit))
                .getContent();
    }

    private List<BookEntity> getPopularBooks(Integer offset, Integer limit) {
        return bookRepository
                .findAll(getPageable(offset, limit))
                .getContent();
    }

    private BookDto createBookDto(BookEntity book) {
        return BookDto.builder()
                .id(book.getId())
                .slug(book.getSlug())
                .title(book.getTitle())
                .image(book.getImage())
                .authors(null)
                .discount(Integer.valueOf(book.getDiscount()))
                .isBestseller(book.getIsBestseller() == 1)
                //.rating()
                //.status()
                .price(book.getPrice())
                .discountPrice(book.getPrice() - book.getDiscount() * book.getPrice() / 100)
                .build();
    }

    private Pageable getPageable(Integer offset, Integer limit) {
        return PageRequest.of(offset, limit);
    }
}
