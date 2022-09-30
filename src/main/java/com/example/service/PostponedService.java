package com.example.service;

import com.example.mapper.BookMapper;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class PostponedService extends GeneralContentService {

  public PostponedService(BookRepository bookRepository,
      BookMapper mapper) {
    super(bookRepository, mapper);
  }
}
