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

  private final BookReviewRepository bookReviewRepository;
  private final BookReviewMapper mapper;
  private final BookRepository bookRepository;
  private final BookReviewLikeRepository bookReviewLikeRepository;
  private final UserRegisterService userRegisterService;

  private static final int MIN_LENGTH_TEXT = 20;
  private static final String ERROR_TEXT = "Отзыв слишком короткий. Напишите, пожалуйста, более развёрнутый отзыв";
  private final UtilityService utilityService;


  @Transactional
  public ResultErrorResponse addBookReview(BookReviewRequest request,
      HttpServletRequest servletRequest) {
    if (request.getText().length() < MIN_LENGTH_TEXT) {
      return utilityService.errorsResponse(ERROR_TEXT);
    }

    BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow();
    UserEntity user = userRegisterService.getRegisteredUser(servletRequest);

    log.debug("book - " + book + " user - " + user);

    bookReviewRepository.save(
        BookReviewEntity.builder()
            .book(book)
            .user(user)
            .time(utilityService.getTimeNow())
            .text(request.getText())
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
  public ResultErrorResponse addRateReviewBook(RateBookReviewRequest request,
      HttpServletRequest servletRequest) {
    UserEntity user = userRegisterService.getRegisteredUser(servletRequest);
    BookReviewEntity bookReviewEntity = bookReviewRepository.findById(request.getReviewId())
        .orElseThrow();
    BookReviewLikeEntity bookReviewLikeEntity = bookReviewLikeRepository.findBookReviewLikeEntityByReviewIdAndUser(
        request.getReviewId(),
        user);
    ResultErrorResponse resultErrorResponse;
    if (bookReviewLikeEntity == null) {
      bookReviewLikeRepository.save(BookReviewLikeEntity.builder()
          .review(bookReviewEntity)
          .user(user)
          .time(utilityService.getTimeNow())
          .value(request.getValue())
          .build()
      );
      resultErrorResponse = utilityService.getResultTrue();
    } else {
      if (bookReviewLikeEntity.getValue() == request.getValue()) {
        resultErrorResponse = utilityService.getResultFalse();
      } else {
        bookReviewLikeEntity.setValue(request.getValue());
        bookReviewLikeEntity.setTime(utilityService.getTimeNow());
        bookReviewLikeRepository.save(bookReviewLikeEntity);
        resultErrorResponse = utilityService.getResultTrue();
      }
    }
    return resultErrorResponse;
  }
}
