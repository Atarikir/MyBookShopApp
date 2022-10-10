package com.example.api.response;

import com.example.data.author.AuthorEntity;
import com.example.data.book.review.BookReviewEntity;
import com.example.data.tag.TagEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

  private int id;
  private LocalDate pubDate;
  private Short isBestseller;
  private String slug;
  private String title;
  private String image;
  private String description;
  private Integer price;
  private Integer discount;
  private Short bookRating;
  private Float bookPopularity;
  private Integer discountPrice;
  private List<BookReviewEntity> bookReviewEntityList;
  private List<AuthorEntity> authorEntityList;
  private List<TagEntity> tagEntityList;
}
