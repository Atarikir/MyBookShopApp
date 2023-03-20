package com.example.controller.api;

import com.example.api.request.BookChangeStatusRequest;
import com.example.api.response.ResultErrorResponse;
import com.example.service.GeneralContentService;
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
public class ContentApiController {

  private final GeneralContentService generalContentService;

  @PostMapping("/changeBookStatus")
  public ResponseEntity<ResultErrorResponse> changeBookStatus(
      @RequestBody BookChangeStatusRequest bookChangeStatusRequest,
      HttpServletRequest httpServletRequest) {
//    log.info("request - " + bookChangeStatusRequest);
    return ResponseEntity.ok(
        generalContentService.changeBookStatus(bookChangeStatusRequest, httpServletRequest));
  }
}
