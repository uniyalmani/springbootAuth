package com.example.practiceApi.springbootpractice.service;


import com.example.practiceApi.springbootpractice.dtos.AuthenticationRequest;
import com.example.practiceApi.springbootpractice.dtos.AuthenticationResponse;
import com.example.practiceApi.springbootpractice.dtos.RegisterRequest;
import com.example.practiceApi.springbootpractice.model.User;
import com.example.practiceApi.springbootpractice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final UserRepository repository;

    private  final PasswordEncoder passwordEncoder;

    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request){


        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);


        repository.save(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            if (e instanceof UsernameNotFoundException) {
                // User not found
                return AuthenticationResponse.builder()
                        .error("User not found with email: " + request.getEmail())
                        .build();
            } else {
                // Invalid email or password
                return AuthenticationResponse.builder()
                        .error("Invalid email or password")
                        .build();
            }
        }

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + request.getEmail()));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
