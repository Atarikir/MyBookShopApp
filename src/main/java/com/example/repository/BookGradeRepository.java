package com.example.repository;

import com.example.data.book.BookEntity;
import com.example.data.book.BookGradeEntity;
import com.example.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookGradeRepository extends JpaRepository<BookGradeEntity, Integer> {

  BookGradeEntity findByBookAndUser(BookEntity book, UserEntity user);
}
