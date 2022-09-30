package com.example.data.book.review;

import com.example.data.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review_like")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "review_id", columnDefinition = "INT NOT NULL")
  private BookReviewEntity review;

  @ManyToOne
  @JoinColumn(name = "user_id", columnDefinition = "INT NOT NULL")
  private UserEntity user;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime time;

  @Column(columnDefinition = "SMALLINT NOT NULL")
  private short value;
}
