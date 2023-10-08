package com.basket.BasketballSystem.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @NotEmpty(message = "el usuario no puede estar vacio") @NotNull(message = "el usuario no puede ser nulo") @ NotBlank(message = "el usuario no puede estar en blanco")
    private String usuario;

    @Column(unique = true)
    @NotEmpty(message = "el email no puede estar vacio") @NotNull(message = "el email no puede ser nulo") @NotBlank(message = "el email no puede estar en blanco")
    private String email;
    @NotEmpty(message = "la contraseña no puede estar vacio") @NotNull(message = "la contraseña no puede ser nulo") @NotBlank(message = "la contraseña no puede estar en blanco")

    private String password;
    @NotEmpty(message = "el nombre no puede estar vacio") @NotNull(message = "el nombre no puede ser nulo") @NotBlank(message = "el nombre no puede estar en blanco")

    private String nombre;

    private LocalDate fechaNacimiento;
    @NotEmpty(message = "el apellido no puede estar vacio") @NotNull(message = "el apellido no puede ser nulo") @NotBlank(message = "el apellido no puedo estar en blanco")
    String apellido;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Integer getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}