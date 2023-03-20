package com.example.repository;

import com.example.data.book.links.Book2GenreEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2GenreRepository extends JpaRepository<Book2GenreEntity, Integer> {

  List<Book2GenreEntity> findByGenreIdIn(List<Integer> genreIdIn);
}
