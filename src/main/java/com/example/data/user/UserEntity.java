package com.example.data.user;

import com.example.data.book.BookEntity;
import com.example.data.book.review.BookReviewLikeEntity;
import com.example.data.book.review.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  @Column(columnDefinition = "VARCHAR(255)")
  private String name;

  @Column(columnDefinition = "VARCHAR(255)")
  private String email;

  @Column(columnDefinition = "VARCHAR(255)")
  private String phone;

  @Column(columnDefinition = "VARCHAR(255)")
  private String password;

  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserContactEntity> userContactList;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<BookReviewLikeEntity> likeList;

  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<MessageEntity> messageList;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

  @Override
  public String toString() {
    return "UserEntity{" +
        "id=" + id +
        ", regTime=" + regTime +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
