package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.links.Book2AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2AuthorRepository extends JpaRepository<Book2AuthorEntity, Integer> {

    //    @Query("SELECT b2a.authorId FROM Book2AuthorEntity b2a WHERE b2a.bookId = ?1 AND b2a.sortIndex < ?2")
//    Integer getAuthorBySortIndex(int bookId, int sortIndex);

    @Query(value = "SELECT b2a.author_id FROM book2author as b2a WHERE b2a.book_id = :book_id and b2a.sort_index < 2",
            nativeQuery = true)
    Integer getAuthorBySortIndex(@Param("book_id") BookEntity book);
}
