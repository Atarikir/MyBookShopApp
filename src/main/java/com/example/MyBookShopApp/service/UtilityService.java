package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.ResultErrorResponse;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

  public ResultErrorResponse getResultTrue() {
    return ResultErrorResponse.builder()
        .result(true)
        .build();
  }

  public ResultErrorResponse getResultFalse() {
    return ResultErrorResponse.builder()
        .result(false)
        .build();
  }
}
