package com.example.MyBookShopApp.data.book;

import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.book.file.BookFileEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.data.tag.TagEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "pub_date", columnDefinition = "DATE NOT NULL")
  private LocalDate pubDate;

  @Column(name = "is_bestseller", columnDefinition = "SMALLINT NOT NULL")
  private Short isBestseller;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String title;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String image;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "INT NOT NULL")
  private Integer price;

  @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
  private Short discount;

  @Column(columnDefinition = "SMALLINT DEFAULT 0")
  private Short bookRating;

  @Column(columnDefinition = "FLOAT DEFAULT 0")
  private Float bookPopularity;

  @OneToMany(mappedBy = "bookId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<BookGradeEntity> bookGradeList;

//  @OneToMany(mappedBy = "bookEntity")
//  private List<BookFileEntity> bookFileEntityList = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2author",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "author_id")}
  )
  private List<AuthorEntity> authorEntityList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2genre",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "genre_id")}
  )
  private List<GenreEntity> genreEntityList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2user",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<UserEntity> userEntityList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "file_download",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<UserEntity> userEntities;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "balance_transaction",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<UserEntity> userTransactList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book_review",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<UserEntity> userReviewList;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2tag",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private List<TagEntity> tagEntityList;
}
