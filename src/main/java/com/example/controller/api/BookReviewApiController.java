package com.example.controller.api;

import com.example.api.request.BookReviewRequest;
import com.example.api.request.RateBookReviewRequest;
import com.example.api.response.ResultErrorResponse;
import com.example.service.BookReviewService;
import javax.servlet.http.HttpServletRequest;
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
  public ResponseEntity<ResultErrorResponse> addReview(@RequestBody BookReviewRequest request,
      HttpServletRequest servletRequest) {
    return ResponseEntity.ok(bookReviewService.addBookReview(request, servletRequest));
  }

  @PostMapping("/rateBookReview")
  public ResponseEntity<ResultErrorResponse> addRateBookReview(
      @RequestBody RateBookReviewRequest request, HttpServletRequest servletRequest) {
    return ResponseEntity.ok(bookReviewService.addRateReviewBook(request, servletRequest));
  }
}