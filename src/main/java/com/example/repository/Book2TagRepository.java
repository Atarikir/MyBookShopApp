package com.example.repository;

import com.example.data.book.links.Book2TagEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2TagRepository extends JpaRepository<Book2TagEntity, Integer> {

  List<Book2TagEntity> findByTagIdIn(List<Integer> tagIdList);
}
