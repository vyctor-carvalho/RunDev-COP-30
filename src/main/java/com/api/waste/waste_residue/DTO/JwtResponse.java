package com.api.waste.waste_residue.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer"; // Tipo do token
    private Long expiresIn = 86400000L; // 1 dia de expiração

    public JwtResponse(String token) {
        this.token = token;
    }

    // Getters e setters
}
