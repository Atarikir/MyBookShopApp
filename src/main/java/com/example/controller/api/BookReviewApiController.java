package com.example.controller.api;

import com.example.api.response.ResultErrorResponse;
import com.example.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookReviewApiController {

  private final BookReviewService bookReviewService;

  @PostMapping("/bookReview")
  public ResponseEntity<ResultErrorResponse> addReview(@RequestParam Integer bookId,
      @RequestParam String text) {
    return ResponseEntity.ok(bookReviewService.addBookReview(bookId, text));
  }

  @PostMapping("/rateBookReview")
  public ResponseEntity<ResultErrorResponse> addRateBookReview(@RequestParam Integer reviewId,
      @RequestParam Short value) {
    return ResponseEntity.ok(bookReviewService.addRateReviewBook(reviewId, value));
  }
}
