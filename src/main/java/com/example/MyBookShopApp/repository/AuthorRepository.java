package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    AuthorEntity findById(int id);
}
