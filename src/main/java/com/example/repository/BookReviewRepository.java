package com.example.repository;

import com.example.data.book.BookEntity;
import com.example.data.book.review.BookReviewEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Integer> {

  List<BookReviewEntity> findByBook(BookEntity book);

  BookReviewEntity findBookReviewEntityByBook(BookEntity book);
}
