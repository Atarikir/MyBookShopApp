package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BooksPageResponse;
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

    public List<BookDto> getRecommendedBooksList(Integer offset, Integer limit) {
        return getBookDtoList(getRecommendedBooks(offset, limit));
    }

    public List<BookDto> getRecentBooksList(Integer offset, Integer limit) {
        return getBookDtoList(getRecentBooks(offset, limit));
    }

    public List<BookDto> getPopularBooksList(Integer offset, Integer limit) {
        return getBookDtoList(getPopularBooks(offset, limit));
    }

//---------------------------------------------------------------------------------------------------------------------

//    public List<BookDto> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
//        return getBookDtoList(getSearchResultsBooks(searchWord, offset, limit));
//    }
//
//    private Page<BookEntity> getSearchResultsBooks(String searchWord, Integer offset, Integer limit) {
//        return bookRepository.findBookByTitleContaining(searchWord, getPageable(offset, limit));
//    }
//
//    public BooksPageResponse getPageOfSearchResultsBooks(String searchWord, Integer offset, Integer limit) {
//        Page<BookEntity> books = getSearchResultsBooks(searchWord, offset, limit);
//        return getBooksPageResponse(books);
//    }

//---------------------------------------------------------------------------------------------------------------------

    public BooksPageResponse getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Page<BookEntity> books = getRecommendedBooks(offset, limit);
        return getBooksPageResponse(books);
    }

    public BooksPageResponse getPageOfRecentBooks(Integer offset, Integer limit) {
        Page<BookEntity> books = getRecentBooks(offset, limit);
        return getBooksPageResponse(books);
    }

    public BooksPageResponse getPageOfPopularBooks(Integer offset, Integer limit) {
        Page<BookEntity> books = getPopularBooks(offset, limit);
        return getBooksPageResponse(books);
    }

    //TODO: реализовать выборку рекомендованных книг
    public Page<BookEntity> getRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.findByPriceBetween(0, 999, getPageable(offset, limit));
    }

    //TODO: реализовать выборку новых книг
    private Page<BookEntity> getRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findByPriceBetween(1000, 1999, getPageable(offset, limit));
    }

    //TODO: реализовать выборку популярных книг
    private Page<BookEntity> getPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findByPriceBetween(2000, 3000, getPageable(offset, limit));
    }

    private BooksPageResponse getBooksPageResponse(Page<BookEntity> books) {
        List<BookDto> bookDtoList = getBookDtoList(books);
        return new BooksPageResponse(
                (int) books.getTotalElements(),
                bookDtoList
        );
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
