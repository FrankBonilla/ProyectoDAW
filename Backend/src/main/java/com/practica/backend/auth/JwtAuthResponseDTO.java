package com.practica.backend.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthResponseDTO {

    private String tokenAcceso;
    private String tipoToken = "Bearer";

    /**Constructores**/
    public JwtAuthResponseDTO(String tokenAcceso, String tipoToken) {
        this.tokenAcceso = tokenAcceso;
        this.tipoToken = tipoToken;
    }

    public JwtAuthResponseDTO(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }
}
