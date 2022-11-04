package com.example.repository;

import com.example.data.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  UserEntity findByName(String name);

  UserEntity findBookstoreUserByEmail(String email);
}
