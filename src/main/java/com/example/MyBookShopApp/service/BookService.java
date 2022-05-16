package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

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

    public List<BookDto> getRecentBooksList(String from, String to, Integer offset, Integer limit) {
        return getBookDtoList(getRecentBooks(from, to, offset, limit));
    }

    public List<BookDto> getPopularBooksList(Integer offset, Integer limit) {
        return getBookDtoList(getPopularBooks(offset, limit));
    }

    public BooksPageResponse getPageOfRecommendedBooks(Integer offset, Integer limit) {
        return getBooksPageResponse(getRecommendedBooks(offset, limit), getRecommendedBooksList(offset, limit));
    }

    public BooksPageResponse getPageOfRecentBooks(String from, String to, Integer offset, Integer limit) {
        return getBooksPageResponse(getRecentBooks(from, to, offset, limit), getRecentBooksList(from, to, offset, limit));
    }

    public BooksPageResponse getPageOfPopularBooks(Integer offset, Integer limit) {
        return getBooksPageResponse(getPopularBooks(offset, limit), getPopularBooksList(offset, limit));
    }

    //TODO: реализовать выборку рекомендованных книг с аутентификацией пользователя
    private Page<BookEntity> getRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.getBooksSortedByPubDateAndBookRatingValue(getPageable(offset, limit));
    }

    //TODO: реализовать выборку новых книг, если отсутствует одна дата
    private Page<BookEntity> getRecentBooks(String from, String to, Integer offset, Integer limit) {
        Page<BookEntity> books;
        if (from == null && to == null) {
            books = bookRepository.findByOrderByPubDateDesc(getPageable(offset, limit));
        } else {
            return bookRepository.findByPubDateBetweenOrderByPubDateDesc(from, to, getPageable(offset, limit));
        }

        return books;
    }

    private Page<BookEntity> getPopularBooks(Integer offset, Integer limit) {
        return bookRepository.getBooksSortedByBookPopularityValue(getPageable(offset, limit));
    }

    private BooksPageResponse getBooksPageResponse(Page<BookEntity> books, List<BookDto> bookDtoList) {
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
}
