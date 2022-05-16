package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRatingEntity, Integer> {

    @Query("SELECT br.bookId FROM BookRatingEntity br WHERE br.bookId = ?1")
    BookEntity getBookId(BookEntity bookId);

    @Query("SELECT br.value FROM BookRatingEntity br WHERE br.bookId = ?1")
    Integer getValueRating(BookEntity bookId);
}
