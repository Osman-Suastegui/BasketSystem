package com.basket.BasketballSystem.auth;

import com.basket.BasketballSystem.usuarios.Genero;
import com.basket.BasketballSystem.usuarios.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    private String usuario;
    private String password;

    private String email;
    private String nombre;
    private String apellido;
    @Enumerated(EnumType.STRING)

    private Rol rol;
    @Enumerated(EnumType.STRING)
    private Genero genero;

}
