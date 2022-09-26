package com.example.MyBookShopApp.data.book.links;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2genre")
@Getter
@Setter
public class Book2GenreEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "book_id", columnDefinition = "INT NOT NULL")
  private int bookId;

  @Column(name = "genre_id", columnDefinition = "INT NOT NULL")
  private int genreId;
}
