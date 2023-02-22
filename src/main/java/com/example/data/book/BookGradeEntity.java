package com.example.data.book;

import com.example.data.user.UserEntity;
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

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id", nullable = false, referencedColumnName = "id")
  private BookEntity book;

  @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private UserEntity user;
}
