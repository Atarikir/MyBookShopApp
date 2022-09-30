package com.example.service;

import com.example.api.response.ResultErrorResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

  public ResultErrorResponse errorsRequest(String errorText) {
    return ResultErrorResponse.builder()
        .result(false)
        .error(errorText)
        .build();
  }

  public LocalDateTime getTimeNow() {
    return LocalDateTime.now(ZoneId.of("UTC"));
  }
}
