package com.example.mapper;

import com.example.api.response.BookReviewDto;
import com.example.data.book.review.BookReviewEntity;
import com.example.repository.BookReviewLikeRepository;
import com.example.service.BookReviewService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class BookReviewMapper {

  @Autowired
  private BookReviewLikeRepository bookReviewLikeRepository;

  BookReviewDto entityToDto(BookReviewEntity bookReviewEntity) {
    BookReviewDto bookReviewDto = new BookReviewDto();
    bookReviewDto.setId(bookReviewEntity.getId());
    bookReviewDto.setBook(bookReviewEntity.getBook());
    bookReviewDto.setUser(bookReviewEntity.getUser());
    bookReviewDto.setText(bookReviewEntity.getText());
    bookReviewDto.setTime(convertLocalDateTimeToString(bookReviewEntity.getTime()));
    bookReviewDto.setLikes(this.getBookReviewLikes(bookReviewEntity, BookReviewService.LIKE_VALUE));
    bookReviewDto.setDislikes(
        this.getBookReviewLikes(bookReviewEntity, BookReviewService.DISLIKE_VALUE));
    bookReviewDto.setReviewRating(bookReviewEntity.getReviewRating());
    return bookReviewDto;
  }

  public abstract List<BookReviewDto> listEntityToDtoList(
      List<BookReviewEntity> bookReviewEntityList);

  String convertLocalDateTimeToString(LocalDateTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return time.plusHours(3).format(formatter);
  }

  public Long getBookReviewLikes(BookReviewEntity bookReview, short value) {
    return bookReviewLikeRepository.countBookReviewLikeEntitiesByReviewAndValue(bookReview, value);
  }
}
