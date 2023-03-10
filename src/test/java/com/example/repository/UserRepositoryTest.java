package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.BaseTest;
import com.example.data.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends BaseTest {

  private final UserRepository userRepository;

  @Autowired
  UserRepositoryTest(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  //@Test
  void addNewUser() {
    UserEntity user = new UserEntity();
    user.setPassword("123456");
    user.setEmail("test@test.test");
    user.setName("Tester");
    user.setPhone("9991234567");

    assertNotNull(userRepository.save(user));
  }
}