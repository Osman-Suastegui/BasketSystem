package com.basket.BasketballSystem.auth;

import com.basket.BasketballSystem.usuarios.Genero;
import com.basket.BasketballSystem.usuarios.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    @NotBlank(message = "El usuario no puede estar en blanco")

    private String usuario;

    @NotBlank(message = "La contraseña no puede estar en blanco")

    private String password;

    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email debe ser una dirección de correo válida")
    private String email;

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar en blanco")
    private String apellido;

    private LocalDate fechaNacimiento;

    @NotNull(message = "El rol no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    private Genero genero;



}
