package com.example.api.response;

import com.example.data.book.BookEntity;
import com.example.data.book.review.BookReviewLikeEntity;
import com.example.data.user.UserEntity;
import java.util.List;
import lombok.Data;

@Data
public class BookReviewDto {

  private int id;
  private BookEntity book;
  private UserEntity user;
  private String time;
  private String text;
  private List<BookReviewLikeEntity> bookReviewLikeEntityList;
}
