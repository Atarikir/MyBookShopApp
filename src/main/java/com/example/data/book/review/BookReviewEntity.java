package com.example.data.book.review;

import com.example.data.book.BookEntity;
import com.example.data.user.UserEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id", nullable = false, referencedColumnName = "id")
  private BookEntity book;

  @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private UserEntity user;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime time;

  @Column(columnDefinition = "TEXT NOT NULL")
  private String text;

  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<BookReviewLikeEntity> bookReviewLikeEntityList;
}
