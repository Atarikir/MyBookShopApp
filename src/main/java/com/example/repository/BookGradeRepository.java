package com.example.repository;

import com.example.data.book.BookGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookGradeRepository extends JpaRepository<BookGradeEntity, Integer> {

}
