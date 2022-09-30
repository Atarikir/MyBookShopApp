package com.example.service;

import com.example.api.response.BookDto;
import com.example.api.response.BookResponseDto;
import com.example.api.response.BooksListPageResponse;
import com.example.data.book.BookEntity;
import com.example.mapper.BookMapper;
import com.example.repository.BookRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class BookService {

  private final BookMapper bookMapper;
  private final BookRepository bookRepository;
  private final ResourceStorage storage;

  public BookService(BookRepository bookRepository, BookMapper bookMapper, ResourceStorage storage) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
    this.storage = storage;
  }

  public List<BookEntity> getBooksData() {
    return bookRepository.findAll();
  }

  public List<BookResponseDto> getBooksByGenreSlugList(String slug, int offset, int limit) {
    return bookMapper.pageEntityToResponseDtoList(getBooksByGenreSlug(slug, offset, limit));
  }

  public List<BookResponseDto> getBooksByTagSlugList(String slug, Integer offset, Integer limit) {
    return bookMapper.pageEntityToResponseDtoList(getBooksByTagSlug(slug, offset, limit));
  }

  public List<BookResponseDto> getBooksByAuthorSlugList(String slug, int offset, int limit) {
    return bookMapper.pageEntityToResponseDtoList(getBooksByAuthorSlug(slug, offset, limit));
  }

  public BookDto getBookDtoBySlug(String slug) {
    return bookMapper.bookEntityToBookDto(getBookBySlug(slug));
  }

  private BookEntity getBookBySlug(String slug) {
    return bookRepository.findBySlug(slug);
  }

  public List<BookResponseDto> getRecommendedBooksList(Integer offset, Integer limit) {
    return bookMapper.pageEntityToResponseDtoList(getRecommendedBooks(offset, limit));
  }

  public List<BookResponseDto> getRecentBooksList(String from, String to, Integer offset,
      Integer limit) {
    return bookMapper.pageEntityToResponseDtoList(getRecentBooks(from, to, offset, limit));
  }

  public List<BookResponseDto> getPopularBooksList(Integer offset, Integer limit) {
    return bookMapper.pageEntityToResponseDtoList(getPopularBooks(offset, limit));
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

  public BooksListPageResponse getPageOfBooksByTagId(Integer id, Integer offset, Integer limit) {
    return bookMapper.toListResponse(getBooksByTagId(id, offset, limit));
  }

  public BooksListPageResponse getPageOfBooksByAuthorId(Integer id, Integer offset, Integer limit) {
    return bookMapper.toListResponse(getBooksByAuthorId(id, offset, limit));
  }

  public BooksListPageResponse getPageOfBooksByGenreId(Integer id, Integer offset, Integer limit) {
    return bookMapper.toListResponse(getBooksByGenreId(id, offset, limit));
  }

  public void saveImageBook(MultipartFile file, String slug) throws IOException {
    log.info("slug in BookService - " + slug);
    String savePath = storage.saveNewBookImage(file, slug);
    BookEntity bookToUpdate = bookRepository.findBySlug(slug);
    bookToUpdate.setImage(savePath);
    bookRepository.save(bookToUpdate); //save new path in db here
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

  private Page<BookEntity> getBooksByTagId(Integer id, Integer offset, Integer limit) {
    return bookRepository.getBooksByTagIdPage(id, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByAuthorId(Integer id, Integer offset, Integer limit) {
    return bookRepository.getBooksByAuthorIdPage(id, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByGenreId(Integer id, Integer offset, Integer limit) {
    return bookRepository.getBooksByGenreIdPage(id, getPageable(offset, limit));
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
