package com.example.repository;

import com.example.data.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {

  TagEntity findBySlugContaining(String slug);
}
