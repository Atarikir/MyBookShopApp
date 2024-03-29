package com.example.data.book.links;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book2user")
@Getter
@Setter
public class Book2UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime time;

  @Column(name = "type_id", columnDefinition = "INT NOT NULL")
  private int typeId;

  @Column(name = "book_id", columnDefinition = "INT NOT NULL")
  private int bookId;

  @Column(name = "user_id", columnDefinition = "INT NOT NULL")
  private int userId;
}
