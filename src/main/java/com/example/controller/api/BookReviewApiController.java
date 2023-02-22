package com.example.controller.api;

import com.example.api.request.BookReviewRequest;
import com.example.api.request.RateBookReviewRequest;
import com.example.api.response.ResultErrorResponse;
import com.example.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookReviewApiController {

  private final BookReviewService bookReviewService;

  @PostMapping("/bookReview")
  public ResponseEntity<ResultErrorResponse> addReview(@RequestBody BookReviewRequest request) {
    return ResponseEntity.ok(bookReviewService.addBookReview(request));
  }

  @PostMapping("/rateBookReview")
  public ResponseEntity<ResultErrorResponse> addRateBookReview(@RequestBody RateBookReviewRequest request) {
    return ResponseEntity.ok(bookReviewService.addRateReviewBook(request));
  }
}