package com.example.controller.api;

import com.example.api.request.RateBookRequest;
import com.example.api.response.ResultErrorResponse;
import com.example.service.BookRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookRatingApiController {

  private final BookRatingService bookRatingService;

  @PostMapping("/rateBook")
  public ResponseEntity<ResultErrorResponse> addBookRating(@RequestBody RateBookRequest request) {
    return ResponseEntity.ok(bookRatingService.addBookRating(request));
  }
}
