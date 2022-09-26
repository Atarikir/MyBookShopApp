package com.example.MyBookShopApp.data.book.file;

import com.example.MyBookShopApp.data.book.BookEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Getter
@Setter
public class BookFileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String hash;

  @Column(columnDefinition = "INT NOT NULL")
  private int typeId;

  @Column(columnDefinition = "VARCHAR NOT NULL")
  private String path;

//  @ManyToOne
//  @JoinColumn(name = "book_id", referencedColumnName = "id")
//  private BookEntity bookEntity;
}
