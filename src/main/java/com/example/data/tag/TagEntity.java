package com.example.data.tag;

import com.example.data.book.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String name;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String slug;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book2tag",
      joinColumns = {@JoinColumn(name = "tag_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")}
  )
  private List<BookEntity> bookList;
}
