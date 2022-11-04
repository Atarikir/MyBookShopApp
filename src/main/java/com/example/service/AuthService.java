package com.example.service;

import com.example.api.response.ContactConfirmationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  public ContactConfirmationResponse contactConfirmation() {
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult("true");
    return response;
  }
}
