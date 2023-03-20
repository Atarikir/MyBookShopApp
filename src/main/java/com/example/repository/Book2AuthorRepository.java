package com.example.repository;

import com.example.data.book.links.Book2AuthorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Book2AuthorRepository extends JpaRepository<Book2AuthorEntity, Integer> {

  @Query("SELECT b2a.authorId FROM Book2AuthorEntity b2a WHERE b2a.bookId = ?1 AND b2a.sortIndex < 2")
  Integer getAuthorIdBySortIndex(int bookId);

  @Query("SELECT b2a.bookId FROM Book2AuthorEntity b2a WHERE b2a.bookId = ?1")
  Integer getBookId(int id);

  List<Book2AuthorEntity> findByAuthorIdIn(List<Integer> authorIdList);
}
