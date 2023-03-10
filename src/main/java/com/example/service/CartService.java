package com.example.service;

import com.example.mapper.BookMapper;
import com.example.repository.Book2UserRepository;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService extends GeneralContentService {

  public CartService(UserRegisterService userRegisterService, BookRepository bookRepository,
      BookMapper bookMapper, Book2UserRepository book2UserRepository) {
    super(userRegisterService, bookRepository, bookMapper, book2UserRepository);
  }
}
