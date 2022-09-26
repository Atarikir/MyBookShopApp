package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review_like")
@Getter
@Setter
public class BookReviewLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "review_id", columnDefinition = "INT NOT NULL")
  private int reviewId;

  @ManyToOne
  @JoinColumn(name = "user_id", columnDefinition = "INT NOT NULL")
  private UserEntity userId;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime time;

  @Column(columnDefinition = "SMALLINT NOT NULL")
  private short value;
}
