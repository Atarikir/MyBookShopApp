package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.data.book.review.BookReviewEntity;
import com.example.mapper.BookReviewMapper;
import com.example.repository.BookRepository;
import com.example.repository.BookReviewLikeRepository;
import com.example.repository.BookReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookReviewServiceTest {

  @Mock
  private BookReviewMapper bookReviewMapper;
  @Mock
  private BookRepository bookRepository;
  @Mock
  private UserRegisterService userRegisterService;
  @Mock
  private BookReviewRepository bookReviewRepository;
  @Mock
  private BookReviewLikeRepository bookReviewLikeRepository;
  private final BookReviewService bookReviewService;
  private long countLikes;
  private long countDislikes;
  private BookReviewEntity bookReview;

  BookReviewServiceTest() {
    MockitoAnnotations.openMocks(this);
    bookReviewService = new BookReviewService(bookReviewMapper, bookRepository, userRegisterService,
        bookReviewRepository, bookReviewLikeRepository);
  }

  @BeforeEach
  void setUp() {
    countLikes = 10;
    countDislikes = 5;

    bookReview = new BookReviewEntity();
    bookReview.setId(1);
  }

  @AfterEach
  void tearDown() {
    bookReview = null;
  }

  @Test
  void reviewRatingCalculationTest() {
    int reviewRating = bookReviewService.reviewRatingCalculation(countLikes, countDislikes);
    assertEquals(5, reviewRating);
  }

  @Test
  void updateBookReviewTest() {
    when(bookReviewMapper.getBookReviewLikes(any(BookReviewEntity.class), anyShort())).thenReturn(
        anyLong());
    when(bookReviewRepository.save(bookReview)).thenReturn(bookReview);

    bookReviewService.updateBookReview(bookReview);

    verify(bookReviewMapper, times(2)).getBookReviewLikes(any(BookReviewEntity.class), anyShort());
    verify(bookReviewRepository).save(bookReview);
  }
}