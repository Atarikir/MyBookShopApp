package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGradeRepository extends JpaRepository<BookGradeEntity, Integer> {
}
