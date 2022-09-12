package com.example.MyBookShopApp.data.genre;

import com.example.MyBookShopApp.data.book.BookEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
public class GenreEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "parent_id", columnDefinition = "INT")
  private Integer parentId;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String name;

  @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<GenreEntity> subGenres;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2genre",
      joinColumns = {@JoinColumn(name = "genre_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  private List<BookEntity> bookList;
}
