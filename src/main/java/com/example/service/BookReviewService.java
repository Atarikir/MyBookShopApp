package com.example.service;

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
import com.example.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookReviewService {

  private final BookReviewRepository bookReviewRepository;
  private final BookReviewMapper mapper;
  private final BookRepository bookRepository;
  private final BookReviewLikeRepository bookReviewLikeRepository;
  private final UserRepository userRepository;

  private static final short LIKE_VALUE = 1;
  private static final short DISLIKE_VALUE = -1;
  private static final int MIN_LENGTH_TEXT = 20;
  private static final String ERROR_TEXT = "Отзыв слишком короткий. Напишите, пожалуйста, более развёрнутый отзыв";
  private final UtilityService utilityService;


  @Transactional
  public ResultErrorResponse addBookReview(Integer bookId, String text) {
    if (text.length() < MIN_LENGTH_TEXT) {
      return utilityService.errorsResponse(ERROR_TEXT);
    }
    BookEntity book = bookRepository.findById(bookId).orElseThrow();
//    UserEntity user = userRepository.findByName(principal.getName());

    UserEntity user = new UserEntity(); //TODO : переделать при добавлении авторизации
    user.setId(1);

    bookReviewRepository.save(BookReviewEntity.builder()
        .book(book)
        .user(user)
        .time(utilityService.getTimeNow())
        .text(text)
        .build());
    return utilityService.getResultTrue();
  }

  public List<BookReviewDto> getBookReviewListByBookSlug(String slug) {
    BookEntity book = bookRepository.findBySlug(slug);
    List<BookReviewEntity> bookReviewEntityList = bookReviewRepository.findBookReviewEntitiesByBook(
        book);
    return mapper.listEntityToDtoList(bookReviewEntityList);
  }

  @Transactional
  public ResultErrorResponse addRateReviewBook(Integer reviewId, Short valueRate) {
    UserEntity user = new UserEntity();
    user.setId(2);
    BookReviewEntity bookReviewEntity = bookReviewRepository.findById(reviewId).orElseThrow();
    BookReviewLikeEntity bookReviewLikeEntity = bookReviewLikeRepository.findBookReviewLikeEntityByReviewIdAndUser(
        reviewId,
        user);
    ResultErrorResponse resultErrorResponse;
    if (bookReviewLikeEntity == null) {
      bookReviewLikeRepository.save(BookReviewLikeEntity.builder()
          .review(bookReviewEntity)
          .user(user)
          .time(utilityService.getTimeNow())
          .value(valueRate)
          .build()
      );
      resultErrorResponse = utilityService.getResultTrue();
    } else {
      if (bookReviewLikeEntity.getValue() == valueRate) {
        resultErrorResponse = utilityService.getResultFalse();
      } else {
        bookReviewLikeEntity.setValue(valueRate);
        bookReviewLikeEntity.setTime(utilityService.getTimeNow());
        bookReviewLikeRepository.save(bookReviewLikeEntity);
        resultErrorResponse = utilityService.getResultTrue();
      }
    }
    return resultErrorResponse;
  }
}
