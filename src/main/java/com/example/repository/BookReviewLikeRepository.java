package com.example.repository;

import com.example.data.book.review.BookReviewEntity;
import com.example.data.book.review.BookReviewLikeEntity;
import com.example.data.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

  BookReviewLikeEntity findByReviewIdAndUser(Integer reviewId, UserEntity user);

  long countBookReviewLikeEntitiesByReviewAndValue(BookReviewEntity bookReview, Short value);

//  List<BookReviewLikeEntity> findBookReviewLikeEntitiesByReviewAndValueEquals(Integer bookReviewId, Short value);

//  long countBookReviewLikeEntityByValueAndAndReviewId(Short value, Integer reviewId);
}
