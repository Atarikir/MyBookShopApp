package com.example.data.book.links;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2tag")
@Getter
@Setter
public class Book2TagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "book_id", columnDefinition = "INT NOT NULL")
  private int bookId;

  @Column(name = "tag_id", columnDefinition = "IN NOT NULL")
  private int tagId;
}
