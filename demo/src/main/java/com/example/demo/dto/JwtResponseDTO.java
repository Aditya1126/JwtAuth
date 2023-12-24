package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponseDTO {

    private String token;
    private String type="Bearer";
    private Long id;
    private String name;
    private List<String> roles;

}
