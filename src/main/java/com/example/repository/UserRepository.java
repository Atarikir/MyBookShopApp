package com.example.repository;

import com.example.data.user.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  UserEntity findByName(String name);

  UserEntity findByEmail(String email);
}
