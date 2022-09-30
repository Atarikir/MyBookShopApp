package com.example.data.user;

import com.example.data.book.BookEntity;
import com.example.data.book.review.BookReviewLikeEntity;
import com.example.data.book.review.MessageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String hash;

  @Column(name = "reg_time", columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime regTime;

  @Column(columnDefinition = "INT NOT NULL")
  private int balance;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String name;

  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserContactEntity> userContactList;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<BookReviewLikeEntity> likeList;

  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<MessageEntity> messageList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2user",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  private List<BookEntity> bookEntityList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "file_download",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  private List<BookEntity> bookEntities;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "balance_transaction",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  private List<BookEntity> bookTransactList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book_review",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  private List<BookEntity> bookReviewList;
}
