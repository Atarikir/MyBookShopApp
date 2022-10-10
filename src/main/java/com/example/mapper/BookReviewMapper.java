package com.example.mapper;

import com.example.api.response.BookReviewDto;
import com.example.data.book.review.BookReviewEntity;
import com.example.repository.BookReviewLikeRepository;
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

  //  @Mapping(source = "time", target = "time", qualifiedByName = "LocalDateTimeToString")
//  @Mapping(source = "id", target = "bookReviewLikes", qualifiedByName = "rateToLikes")
//  @Mapping(source = "id", target = "bookReviewDislikes", qualifiedByName = "rateToDislikes")
  BookReviewDto entityToDto(BookReviewEntity bookReviewEntity) {
    BookReviewDto bookReviewDto = new BookReviewDto();
    bookReviewDto.setId(bookReviewEntity.getId());
    bookReviewDto.setBook(bookReviewEntity.getBook());
    bookReviewDto.setUser(bookReviewEntity.getUser());
    bookReviewDto.setText(bookReviewEntity.getText());
    bookReviewDto.setTime(convertLocalDateTimeToString(bookReviewEntity.getTime()));
    bookReviewDto.setLikes(getBookReviewLikes(bookReviewEntity));
    bookReviewDto.setDislikes(getBookReviewDislikes(bookReviewEntity));
    return bookReviewDto;
  }

  //  @Named("LocalDateTimeToString")
  String convertLocalDateTimeToString(LocalDateTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return time.plusHours(3).format(formatter);
  }

  //  @Named("rateToLikes")
  Long getBookReviewLikes(BookReviewEntity bookReview) {
    return bookReviewLikeRepository.countBookReviewLikeEntitiesByReviewAndValue(bookReview, (short) 1);
  }

  //  @Named("rateToDislikes")
  Long getBookReviewDislikes(BookReviewEntity bookReview) {
    return bookReviewLikeRepository.countBookReviewLikeEntitiesByReviewAndValue(bookReview, (short) -1);
  }

  public abstract List<BookReviewDto> listEntityToDtoList(
      List<BookReviewEntity> bookReviewEntityList);
}
