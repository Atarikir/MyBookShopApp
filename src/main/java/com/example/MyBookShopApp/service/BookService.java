package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BookResponseDto;
import com.example.MyBookShopApp.api.response.BooksListPageResponse;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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

  public List<BookResponseDto> getBooksByGenreSlugList(String slug, int offset, int limit) {
    return bookMapper.pageEntityToDtoList(getBooksByGenreSlug(slug, offset, limit));
  }

  public List<BookResponseDto> getBooksByTagSlugList(String slug, Integer offset, Integer limit) {
    return bookMapper.pageEntityToDtoList(getBooksByTagSlug(slug, offset, limit));
  }

  public List<BookResponseDto> getBooksByAuthorSlugList(String slug, int offset, int limit) {
    return bookMapper.pageEntityToDtoList(getBooksByAuthorSlug(slug, offset, limit));
  }

  public BookDto getBookDtoBySlug(String slug) {
    return bookMapper.bookEntityToBookDto(getBookBySlug(slug));
  }

  private BookEntity getBookBySlug(String slug) {
    return bookRepository.findBySlug(slug);
  }

  public List<BookResponseDto> getRecommendedBooksList(Integer offset, Integer limit) {
    return bookMapper.pageEntityToDtoList(getRecommendedBooks(offset, limit));
  }

  public List<BookResponseDto> getRecentBooksList(String from, String to, Integer offset,
      Integer limit) {
    return bookMapper.pageEntityToDtoList(getRecentBooks(from, to, offset, limit));
  }

  public List<BookResponseDto> getPopularBooksList(Integer offset, Integer limit) {
    return bookMapper.pageEntityToDtoList(getPopularBooks(offset, limit));
  }

  public BooksListPageResponse getPageOfRecommendedBooks(Integer offset, Integer limit) {
    return bookMapper.toListResponse(getRecommendedBooks(offset, limit));
  }

  public BooksListPageResponse getPageOfRecentBooks(String from, String to, Integer offset,
      Integer limit) {
    return bookMapper.toListResponse(getRecentBooks(from, to, offset, limit));
  }

  public BooksListPageResponse getPageOfPopularBooks(Integer offset, Integer limit) {
    return bookMapper.toListResponse(getPopularBooks(offset, limit));
  }

  public BooksListPageResponse getPageOfBooksByTagId(String id, Integer offset, Integer limit) {
    return bookMapper.toListResponse(getBooksByTagId(id, offset, limit));
  }

  //TODO: реализовать выборку рекомендованных книг с аутентификацией пользователя
  private Page<BookEntity> getRecommendedBooks(Integer offset, Integer limit) {
    return bookRepository.findByOrderByBookRatingDescPubDateDesc(getPageable(offset, limit));
  }

  private Page<BookEntity> getRecentBooks(String from, String to, Integer offset, Integer limit) {
    Page<BookEntity> books = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    if (Objects.equals(from, "") && Objects.equals(to, "") || from == null && to == null) {
      books = bookRepository.findByOrderByPubDateDesc(getPageable(offset, limit));
    } else if (Objects.equals(from, "") && !to.equals("") || from == null) {
      LocalDate dateEnd = LocalDate.parse(to, formatter);
      books = bookRepository.findByPubDateBeforeOrderByPubDateDesc(dateEnd,
          getPageable(offset, limit));
    } else if (Objects.equals(to, "") && !Objects.equals(from, "") || to == null) {
      LocalDate dateStart = LocalDate.parse(Objects.requireNonNull(from), formatter);
      books = bookRepository.findByPubDateAfterOrderByPubDateDesc(dateStart,
          getPageable(offset, limit));
    } else if (!Objects.equals(from, "") && !Objects.equals(to, "")) {
      LocalDate dateStart = LocalDate.parse(Objects.requireNonNull(from), formatter);
      LocalDate dateEnd = LocalDate.parse(Objects.requireNonNull(to), formatter);
      books = bookRepository.findByPubDateBetweenOrderByPubDateDesc(dateStart, dateEnd,
          getPageable(offset, limit));
    }

    return books;
  }

  private Page<BookEntity> getPopularBooks(Integer offset, Integer limit) {
    return bookRepository.findByOrderByBookPopularityDesc(getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByTagId(String id, Integer offset, Integer limit) {
    return bookRepository.getBooksByTagIdPage(Integer.valueOf(id), getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByTagSlug(String slug, Integer offset, Integer limit) {
    return bookRepository.getBooksByTagSlugPage(slug, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByGenreSlug(String slug, Integer offset, Integer limit) {
    return bookRepository.getBooksByGenreSlugPage(slug, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByAuthorSlug(String slug, int offset, int limit) {
    return bookRepository.getBooksByAuthorSlugPage(slug, getPageable(offset, limit));
  }

  private Pageable getPageable(Integer offset, Integer limit) {
    return PageRequest.of(offset, limit);
  }
}
