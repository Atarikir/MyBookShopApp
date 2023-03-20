package com.example.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.BaseTest;
import com.example.api.request.RegistrationForm;
import com.example.data.user.UserEntity;
import com.example.repository.UserRepository;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserRegisterServiceTest extends BaseTest {

  private final UserRegisterService userRegisterService;
  private final PasswordEncoder passwordEncoder;
  private RegistrationForm registrationForm;
  @MockBean
  private UserRepository userRepositoryMock;
  @MockBean
  private HttpServletRequest httpServletRequest;
  @MockBean
  private HttpServletResponse httpServletResponse;
  private UserEntity newUser;
  private UserEntity userAnon;

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

    newUser = new UserEntity();

//    userAnon = new UserEntity();
//    userAnon.setRegTime(UtilityService.getTimeNowUTC());
//    userAnon.setName(registrationForm.getName());
//    userAnon.setEmail(registrationForm.getEmail());
//    userAnon.setPhone(registrationForm.getPhone());
//    userAnon.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
  }

  @AfterEach
  void tearDown() {
    registrationForm = null;
    newUser = null;
    userAnon = null;
  }

//  @Test
//  void registerNewUser() {
//    when(userRepositoryMock.findByEmail("new@mail.com")).thenReturn(newUser);
//    when(userRepositoryMock.findByEmail("username")).thenReturn(userAnon);
////    when(userRepositoryMock.save(userAnon)).thenReturn(newUser);
//    userRegisterService.registerNewUser(registrationForm, httpServletRequest,
//        httpServletResponse);
////    assertNotNull(user);
////    assertTrue(passwordEncoder.matches(registrationForm.getPassword(), user.getPassword()));
////    assertTrue(CoreMatchers.is(user.getPhone()).matches(registrationForm.getPhone()));
////    assertTrue(CoreMatchers.is(user.getName()).matches(registrationForm.getName()));
////    assertTrue(CoreMatchers.is(user.getEmail()).matches(registrationForm.getEmail()));
//
//    Mockito.verify(userRepositoryMock, Mockito.times(2)).findByEmail(anyString());
//    Mockito.verify(userRepositoryMock, Mockito.times(1)).save(any(UserEntity.class));
//  }

  //@Test
  void registerNewUser_withEntityExistsException() {
    Mockito.doReturn(new UserEntity()).when(userRepositoryMock)
        .findByEmail("test@mail.ru");

    assertThrows(EntityExistsException.class,
        () -> userRegisterService.registerNewUser(registrationForm, httpServletRequest,
            httpServletResponse));

    verify(userRepositoryMock).findByEmail(registrationForm.getEmail());
  }
}