package com.example.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.example.BaseTest;
import com.example.api.request.RegistrationForm;
import com.example.data.user.UserEntity;
import com.example.repository.UserRepository;
import javax.persistence.EntityExistsException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserRegisterServiceTest extends BaseTest {

  private final UserRegisterService userRegisterService;
  private final PasswordEncoder passwordEncoder;
  private RegistrationForm registrationForm;
  @MockBean
  private UserRepository userRepositoryMock;

  @Autowired
  UserRegisterServiceTest(UserRegisterService userRegisterService,
      PasswordEncoder passwordEncoder) {
    this.userRegisterService = userRegisterService;
    this.passwordEncoder = passwordEncoder;
  }

  @BeforeEach
  void setUp() {
    registrationForm = new RegistrationForm();
    registrationForm.setEmail("test@mail.ru");
    registrationForm.setPassword("test");
    registrationForm.setName("Tester");
    registrationForm.setPhone("9991234567");
  }

  @AfterEach
  void tearDown() {
    registrationForm = null;
  }

//  @Test
//  void registerNewUser() {
//    UserEntity user = userRegisterService.registerNewUser(registrationForm);
//    assertNotNull(user);
//    assertTrue(passwordEncoder.matches(registrationForm.getPassword(), user.getPassword()));
//    assertTrue(CoreMatchers.is(user.getPhone()).matches(registrationForm.getPhone()));
//    assertTrue(CoreMatchers.is(user.getName()).matches(registrationForm.getName()));
//    assertTrue(CoreMatchers.is(user.getEmail()).matches(registrationForm.getEmail()));
//
//    Mockito.verify(userRepositoryMock, Mockito.times(1)).save(any(UserEntity.class));
//  }

//  @Test
//  void registerNewUser_withEntityExistsException() {
//    Mockito.doReturn(new UserEntity()).when(userRepositoryMock)
//        .findByEmail(registrationForm.getEmail());
//
//    assertThrows(EntityExistsException.class, () -> userRegisterService.registerNewUser(registrationForm));
//
//    verify(userRepositoryMock).findByEmail(registrationForm.getEmail());
//  }
}