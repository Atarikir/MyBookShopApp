package com.example.MyBookShopApp.data.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_grade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookGradeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
  private Short value;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id", columnDefinition = "INT NOT NULL")
  private BookEntity bookId;
}
