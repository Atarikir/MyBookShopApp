package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

  List<GenreEntity> findByParentIdIsNull();

  GenreEntity findBySlugContaining(String slug);
}
