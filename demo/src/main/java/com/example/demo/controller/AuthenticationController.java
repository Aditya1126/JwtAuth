package com.example.demo.controller;

import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody UserDTO userDTO){
        JwtResponseDTO jwtReponseDTO = authenticationService.login(userDTO);
        ResponseEntity re = new ResponseEntity(jwtReponseDTO, HttpStatus.OK);
        return re;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDTO signupDTO){
        Long userId = authenticationService.signup(signupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully with id: "+userId);
    }
}
