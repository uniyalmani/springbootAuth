package com.example.practiceApi.springbootpractice.dtos;


import com.example.practiceApi.springbootpractice.model.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Getter
    @Setter
    private String fullName;
    private  String email;

    private String  password;

    private Role role;
}
