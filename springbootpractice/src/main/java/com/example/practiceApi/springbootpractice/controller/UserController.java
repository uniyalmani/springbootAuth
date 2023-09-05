package com.example.practiceApi.springbootpractice.controller;


import com.example.practiceApi.springbootpractice.repository.UserRepository;
import com.example.practiceApi.springbootpractice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class UserController {


    private final UserRepository repository;

    @GetMapping
    public  ResponseEntity<String> testing(){
        return ResponseEntity.ok("hello every thing is fine");
    }
}
