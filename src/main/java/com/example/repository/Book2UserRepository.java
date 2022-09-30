package com.example.repository;

import com.example.data.book.links.Book2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {

}
