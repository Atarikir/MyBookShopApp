package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {

    @Query("SELECT b2u.typeId FROM Book2UserEntity b2u WHERE b2u.userId = ?1 AND b2u.bookId = ?2")
    Integer getTypeIdByBookIdAndUserId(int userId, int bookId);

    @Query("SELECT b2u.userId FROM Book2UserEntity b2u WHERE b2u.userId = ?1")
    Integer getUserId(int id);

    @Query("SELECT b2u.bookId FROM Book2UserEntity b2u WHERE b2u.bookId = ?1")
    Integer getBookId(int id);
}
