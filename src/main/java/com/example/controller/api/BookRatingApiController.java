package com.example.controller.api;

import com.example.api.response.ResultErrorResponse;
import com.example.service.BookRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookRatingApiController {

  private final BookRatingService bookRatingService;

  @PostMapping("/rateBook")
  public ResponseEntity<ResultErrorResponse> addBookRating(@RequestParam Integer bookId,
      @RequestParam Short value) {
    log.debug("bookId" + bookId);
    return ResponseEntity.ok(bookRatingService.addBookRating(bookId, value));
  }
}
