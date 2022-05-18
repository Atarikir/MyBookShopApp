package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Page<BookEntity> findByOrderByPubDateDesc(Pageable pageable);

    Page<BookEntity> findByOrderByBookRatingDescPubDateDesc(Pageable pageable);

    Page<BookEntity> findByPubDateBeforeOrderByPubDateDesc(@Param("to") LocalDate to, Pageable pageable);

    Page<BookEntity> findByPubDateAfterOrderByPubDateDesc(@Param("from") LocalDate from, Pageable pageable);

    Page<BookEntity> findByPubDateBetweenOrderByPubDateDesc(@Param("from") LocalDate from,
                                                            @Param("to") LocalDate to,
                                                            Pageable pageable);

    Page<BookEntity> findByOrderByBookPopularityDesc(Pageable pageable);
}
