package com.example.mapper;

import com.example.api.response.BookReviewDto;
import com.example.data.book.review.BookReviewEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookReviewMapper {

  @Mapping(source = "time", target = "time", qualifiedByName = "LocalDateTimeToString")
  BookReviewDto entityToDto(BookReviewEntity bookReviewEntity);

  @Named("LocalDateTimeToString")
  default String convertLocalDateTimeToString(LocalDateTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return time.plusHours(3).format(formatter);
  }

  List<BookReviewDto> listEntityToDtoList(List<BookReviewEntity> bookReviewEntityList);
}
