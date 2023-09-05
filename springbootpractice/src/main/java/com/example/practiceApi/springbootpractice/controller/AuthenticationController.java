package com.example.practiceApi.springbootpractice.controller;

import com.example.practiceApi.springbootpractice.dtos.AuthenticationRequest;
import com.example.practiceApi.springbootpractice.dtos.AuthenticationResponse;
import com.example.practiceApi.springbootpractice.dtos.RegisterRequest;
import com.example.practiceApi.springbootpractice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;




    @PostMapping("/resister")
    public ResponseEntity<AuthenticationResponse> resister(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        AuthenticationResponse response = service.authenticate(request);
        if (response.getError() != null) {
            // Authentication error occurred, return HTTP 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            // Successful authentication, return HTTP 200 (OK)
            return ResponseEntity.ok(response);
        }
    }
}
