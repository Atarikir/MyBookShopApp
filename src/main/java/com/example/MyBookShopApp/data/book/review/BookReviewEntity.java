package com.example.MyBookShopApp.data.book.review;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review")
@Getter
@Setter
public class BookReviewEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "book_id", columnDefinition = "INT NOT NULL")
  private int bookId;

  @Column(name = "user_id", columnDefinition = "INT NOT NULL")
  private int userId;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime time;

  @Column(columnDefinition = "TEXT NOT NULL")
  private String text;
}
