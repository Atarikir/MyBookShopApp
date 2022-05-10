package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Page<BookEntity> findBookByTitleContaining(String bookTitle, Pageable pageable);

    Page<BookEntity> findByPriceBetween(int from, int to, Pageable pageable);

    Page<BookEntity> findAllById(int id, Pageable pageable);

    Page<BookEntity> findAllByBookGradeListInOrderByBookRatingDesc(List<BookGradeEntity> bookRatingList, Pageable pageable);
}
