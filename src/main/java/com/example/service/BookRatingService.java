package com.example.service;

import com.example.api.response.ResultErrorResponse;
import com.example.data.book.BookEntity;
import com.example.data.book.BookGradeEntity;
import com.example.repository.BookGradeRepository;
import com.example.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookRatingService {

  private final UtilityService utilityService;
  private final BookRepository bookRepository;
  private final BookGradeRepository bookGradeRepository;

  @Transactional
  public ResultErrorResponse addBookRating(Integer bookId, Short valueGrade) {
    BookEntity book = bookRepository.findById(bookId).orElseThrow();
    if (valueGrade != null) {
      bookGradeRepository.save(BookGradeEntity.builder()
          .value(valueGrade)
          .book(book)
          .build());
      short rating = bookRatingCalculation(book);
      book.setBookRating(rating);
      bookRepository.save(book);
      return utilityService.getResultTrue();
    }
    return utilityService.getResultFalse();
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
