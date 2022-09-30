package com.example.repository;

import com.example.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

  List<GenreEntity> findByParentIdIsNull();

  GenreEntity findBySlugContaining(String slug);
}
