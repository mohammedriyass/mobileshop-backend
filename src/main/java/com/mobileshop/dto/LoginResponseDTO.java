package com.mobileshop.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private String name;
    private String email;
    private String role;

}
