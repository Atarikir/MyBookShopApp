package com.example.service;

import com.example.api.request.BookReviewRequest;
import com.example.api.request.RateBookReviewRequest;
import com.example.api.response.BookReviewDto;
import com.example.api.response.ResultErrorResponse;
import com.example.data.book.BookEntity;
import com.example.data.book.review.BookReviewEntity;
import com.example.data.book.review.BookReviewLikeEntity;
import com.example.data.user.UserEntity;
import com.example.mapper.BookReviewMapper;
import com.example.repository.BookRepository;
import com.example.repository.BookReviewLikeRepository;
import com.example.repository.BookReviewRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookReviewService {

  private final BookReviewMapper mapper;
  private final BookRepository bookRepository;
  private final UserRegisterService userRegisterService;
  private final BookReviewRepository bookReviewRepository;
  private final BookReviewLikeRepository bookReviewLikeRepository;

  private static final short MIN_LENGTH_TEXT = 20;
  private static final int REVIEW_RATING_DEFAULT_VALUE = 0;
  private static final String ERROR_TEXT = "Отзыв слишком короткий. Напишите, пожалуйста, более развёрнутый отзыв";
  public static final short LIKE_VALUE = 1;
  public static final short DISLIKE_VALUE = -1;

  @Transactional
  public ResultErrorResponse addBookReview(BookReviewRequest request,
      HttpServletRequest servletRequest) {
    if (request.getText().length() < MIN_LENGTH_TEXT) {
      return UtilityService.errorsResponse(ERROR_TEXT);
    }

    BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow();
    UserEntity user = userRegisterService.getRegisteredUser(servletRequest);

    log.info("book - " + book + " user - " + user);

    bookReviewRepository.save(
        BookReviewEntity.builder()
            .book(book)
            .user(user)
            .time(UtilityService.getTimeNowUTC())
            .text(request.getText())
            .reviewRating(REVIEW_RATING_DEFAULT_VALUE)
            .build());
    return UtilityService.getResultTrue();
  }

  public List<BookReviewDto> getBookReviewListByBookSlug(String slug) {
    BookEntity book = bookRepository.findBySlug(slug);
    List<BookReviewEntity> bookReviewEntityList = bookReviewRepository.findByBookOrderByReviewRatingDesc(
        book);
    return mapper.listEntityToDtoList(bookReviewEntityList);
  }

  @Transactional
  public ResultErrorResponse addRateReviewBook(RateBookReviewRequest request,
      HttpServletRequest servletRequest) {
    UserEntity user = userRegisterService.getRegisteredUser(servletRequest);
    BookReviewEntity bookReviewEntity = bookReviewRepository.findById(request.getReviewId())
        .orElseThrow();
    BookReviewLikeEntity bookReviewLikeEntity = bookReviewLikeRepository.findByReviewIdAndUser(
        request.getReviewId(),
        user);
    ResultErrorResponse resultErrorResponse;
    if (bookReviewLikeEntity == null) {
      BookReviewLikeEntity bookReviewLike = BookReviewLikeEntity.builder()
          .review(bookReviewEntity)
          .user(user)
          .time(UtilityService.getTimeNowUTC())
          .value(request.getValue())
          .build();
      this.saveReviewLike(bookReviewLike);
      this.updateBookReview(bookReviewEntity);
      resultErrorResponse = UtilityService.getResultTrue();
    } else {
      if (bookReviewLikeEntity.getValue() == request.getValue()) {
        resultErrorResponse = UtilityService.getResultFalse();
      } else {
        bookReviewLikeEntity.setValue(request.getValue());
        bookReviewLikeEntity.setTime(UtilityService.getTimeNowUTC());
        this.saveReviewLike(bookReviewLikeEntity);
        this.updateBookReview(bookReviewEntity);
        resultErrorResponse = UtilityService.getResultTrue();
      }
    }
    return resultErrorResponse;
  }

  @Transactional
  public void saveReviewLike(BookReviewLikeEntity bookReviewLikeEntity) {
    bookReviewLikeRepository.save(bookReviewLikeEntity);
  }

  @Transactional
  public void updateBookReview(BookReviewEntity bookReview) {
    long countLikes = mapper.getBookReviewLikes(bookReview, LIKE_VALUE);
    long countDislikes = mapper.getBookReviewLikes(bookReview, DISLIKE_VALUE);
    bookReview.setReviewRating(this.reviewRatingCalculation(countLikes, countDislikes));
    bookReviewRepository.save(bookReview);
  }

  public Integer reviewRatingCalculation(long countLikes, long countDislikes) {
    return (int) (countLikes - countDislikes);
  }
}
