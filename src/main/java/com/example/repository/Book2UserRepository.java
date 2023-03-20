package com.example.repository;

import com.example.data.book.links.Book2UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {

  Book2UserEntity findByBookIdAndUserId(Integer bookId, Integer userId);

  List<Book2UserEntity> findByUserId(Integer userId);

  long countByBookIdAndTypeId(Integer bookId, Integer typeId);
}
