package com.example.demo.dto;

import lombok.Data;

@Data
public class SignUpDTO {

    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;

}
