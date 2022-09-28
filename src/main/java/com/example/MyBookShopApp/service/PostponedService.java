package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.mapper.BookMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class PostponedService extends GeneralContentService {

  public PostponedService(BookRepository bookRepository,
      BookMapper mapper) {
    super(bookRepository, mapper);
  }
}
