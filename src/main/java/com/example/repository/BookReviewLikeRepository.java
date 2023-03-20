package com.example.repository;

import com.example.data.book.review.BookReviewEntity;
import com.example.data.book.review.BookReviewLikeEntity;
import com.example.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

  BookReviewLikeEntity findByReviewIdAndUser(Integer reviewId, UserEntity user);

  long countBookReviewLikeEntitiesByReviewAndValue(BookReviewEntity bookReview, Short value);
}
