package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.links.Book2AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2AuthorRepository extends JpaRepository<Book2AuthorEntity, Integer> {

    @Query("SELECT b2a.authorId FROM Book2AuthorEntity b2a WHERE b2a.bookId = ?1 AND b2a.sortIndex < 2")
    Integer getAuthorIdBySortIndex(int bookId);

    @Query("SELECT b2a.bookId FROM Book2AuthorEntity b2a WHERE b2a.bookId = ?1")
    Integer getBookId(int id);
}
