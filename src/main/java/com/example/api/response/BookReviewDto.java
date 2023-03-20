package com.example.api.response;

import com.example.data.book.BookEntity;
import com.example.data.user.UserEntity;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import lombok.Data;

@Data
public class BookReviewDto {

  private int id;
  private BookEntity book;
  private UserEntity user;
  private String time;
  private String text;
  private Long likes;
  private Long dislikes;
  private Integer reviewRating;

  public String getLittleReview() {
    if (text.length() < 100) {
      return text;
    }
    return text.substring(0, 100);
  }

  public String[] getOtherPartReview() {
    String newString = text.substring(100);
    return Iterables.toArray(Splitter.fixedLength(100).split(newString), String.class);
  }

  public boolean isBigReview() {
    return text.length() >= 100;
  }
}
