package com.example.repository;

import com.example.data.author.AuthorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

  AuthorEntity findById(int id);

  AuthorEntity findBySlugContaining(String slug);

  @Query("select a from AuthorEntity a join Book2AuthorEntity b2a on b2a.authorId = a.id join BookEntity b on b2a.bookId = b.id where b.slug = ?1")
  List<AuthorEntity> getAuthorsByBookSlug(String slug);
}
