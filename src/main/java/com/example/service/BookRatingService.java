package com.example.service;

import com.example.api.request.RateBookRequest;
import com.example.api.response.ResultErrorResponse;
import com.example.data.book.BookEntity;
import com.example.data.book.BookGradeEntity;
import com.example.data.user.UserEntity;
import com.example.repository.BookGradeRepository;
import com.example.repository.BookRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookRatingService {

  private final BookRepository bookRepository;
  private final BookGradeRepository bookGradeRepository;
  private final UserRegisterService userRegisterService;

  @Transactional
  public ResultErrorResponse addBookRating(RateBookRequest request,
      HttpServletRequest servletRequest) {
    if (request.getValue() != null) {
      BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow();
      UserEntity user = userRegisterService.getRegisteredUser(servletRequest);
      BookGradeEntity grade = bookGradeRepository.findByBookAndUser(book, user);
      if (grade != null) {
        grade.setValue(request.getValue());
      } else {
        grade = BookGradeEntity.builder()
            .value(request.getValue())
            .book(book)
            .user(user)
            .build();
      }
      bookGradeRepository.save(grade);
      short rating = bookRatingCalculation(book);
      book.setBookRating(rating);
      bookRepository.save(book);
      return UtilityService.getResultTrue();
    }
    return UtilityService.getResultFalse();
  }

  private Short bookRatingCalculation(BookEntity book) {
    short rating;
    List<BookGradeEntity> bookGradeEntityList = book.getBookGradeList();
    if (bookGradeEntityList.isEmpty()) {
      rating = 0;
    } else {
      int count = bookGradeEntityList.size();
      int sumValue = bookGradeEntityList.stream().mapToInt(BookGradeEntity::getValue).sum();
      rating = (short) Math.round((float) sumValue / count);
    }
    return rating;
  }
}
