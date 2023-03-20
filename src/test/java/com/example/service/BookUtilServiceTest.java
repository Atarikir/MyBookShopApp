package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.data.book.BookEntity;
import com.example.repository.Book2UserRepository;
import com.example.repository.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookUtilServiceTest {

  @Mock
  private BookRepository bookRepository;
  @Mock
  private Book2UserRepository book2UserRepository;
  private final BookUtilService bookUtilService;
  private BookEntity book;
  private long countPaid = 2L;
  private long countCart = 1L;
  private long countPostponed = 1L;

  BookUtilServiceTest() {
    MockitoAnnotations.openMocks(this);
    bookUtilService = new BookUtilService(bookRepository, book2UserRepository);
  }

  @BeforeEach
  void setUp() {
    book = new BookEntity();
    book.setId(1);

    countPaid = 2L;
    countCart = 1L;
    countPostponed = 1L;
  }

  @AfterEach
  void tearDown() {
    book = null;
  }

  @Test
  void popularityCalculationTest() {
    float popularity = bookUtilService.popularityCalculation(countPaid, countCart, countPostponed);
    assertEquals(3.1F, popularity);
  }

  @Test
  void bookUpdateWhenPopularityChangesTest() {
    when(bookRepository.findById(1)).thenReturn(Optional.of(book));
    when(book2UserRepository.countByBookIdAndTypeId(anyInt(), anyInt())).thenReturn(anyLong());
    when(bookRepository.save(book)).thenReturn(book);

    bookUtilService.bookUpdateWhenPopularityChanges(1);

    verify(bookRepository).findById(book.getId());
    verify(book2UserRepository, times(4)).countByBookIdAndTypeId(anyInt(), anyInt());
    verify(bookRepository).save(book);
  }
}