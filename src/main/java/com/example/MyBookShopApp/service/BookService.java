package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookGradeRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookGradeRepository bookGradeRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, BookGradeRepository bookGradeRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookGradeRepository = bookGradeRepository;
    }

    public List<BookEntity> getBooksData() {
        return bookRepository.findAll();
    }

    public List<BookDto> getRecommendedBooksList(Integer offset, Integer limit) {
        Page<BookEntity> books = getRecommendedBooks(offset, limit);
        return getBookDtoList(books);
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
//        Page<BookEntity> books = getAllBooks(offset, limit);
        return getBooksPageResponse(getRecommendedBooks(offset, limit), getRecommendedBooksList(offset, limit));
    }

    public BooksPageResponse getPageOfRecentBooks(Integer offset, Integer limit) {
//        Page<BookEntity> books = getRecentBooks(offset, limit);
        return getBooksPageResponse(getRecentBooks(offset, limit), getRecentBooksList(offset, limit));
    }

    public BooksPageResponse getPageOfPopularBooks(Integer offset, Integer limit) {
//        Page<BookEntity> books = getPopularBooks(offset, limit);
        return getBooksPageResponse(getPopularBooks(offset, limit), getPopularBooksList(offset, limit));
    }

    public Page<BookEntity> getAllBooks(Integer offset, Integer limit) {
        return bookRepository.findAll(getPageable(offset, limit));
    }

    //TODO: реализовать выборку рекомендованных книг с аутентификацией пользователя
    public Page<BookEntity> getRecommendedBooks(Integer offset, Integer limit) {
        List<BookGradeEntity> bookGradeEntities = bookGradeRepository.findAll().stream()
                .filter(distinctByKey(BookGradeEntity::getBookId))
                .collect(Collectors.toList());
        if (bookGradeEntities.isEmpty()) {
            return getRecentBooks(offset, limit);
        } else {
            return bookRepository.findAllByBookGradeListInOrderByBookRatingDesc(bookGradeEntities,
                    getPageable(offset, limit));
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //TODO: реализовать выборку новых книг
    private Page<BookEntity> getRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findByPriceBetween(1000, 1999, getPageable(offset, limit));
    }

    //TODO: реализовать выборку популярных книг
    private Page<BookEntity> getPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findByPriceBetween(2000, 3000, getPageable(offset, limit));
    }

    private BooksPageResponse getBooksPageResponse(Page<BookEntity> books, List<BookDto> bookDtoList) {
//        List<BookDto> bookDtoList = getBookDtoList(books);
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
