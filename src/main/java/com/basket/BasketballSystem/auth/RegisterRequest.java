package com.basket.BasketballSystem.auth;

import com.basket.BasketballSystem.usuarios.Genero;
import com.basket.BasketballSystem.usuarios.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    @NotEmpty(message = "el usuario no puede estar vacio") @NotNull(message = "el usuario no puede ser nulo") @NotBlank(message = "el usuario no puede estar en blanco")

    private String usuario;

    @NotEmpty(message = "la contraseña no puede estar vacio") @NotNull(message = "la contraseña no puede ser nulo") @NotBlank(message = "la contraseña no puede estar en blanco")

    private String password;

    @NotEmpty(message = "el email no puede estar vacio") @NotNull(message = "el email no puede ser nulo") @NotBlank(message = "el email no puede estar en blanco")

    private String email;
    @NotEmpty(message = "el nombre no puede estar vacio") @NotNull(message = "el nombre no puede ser nulo") @NotBlank(message = "el nombre no puede estar en blanco")

    private String nombre;
    @NotEmpty(message = "el apellido no puede estar vacio") @NotNull(message = "el apellido no puede ser nulo") @NotBlank(message = "el apellido no puedo estar en blanco")
    private String apellido;
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Enumerated(EnumType.STRING)
    private Genero genero;

}