package com.basket.BasketballSystem.usuarios.DTO;

import com.basket.BasketballSystem.usuarios.Genero;
import com.basket.BasketballSystem.usuarios.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    @NotBlank(message = "el usuario no puede estar en blanco")

    private String usuario;

   @NotBlank(message = "el email no puede estar en blanco")
   @Email(message = "el email debe ser una dirección de correo válida")
    private String email;
   @NotBlank(message = "el nombre no puede estar en blanco")

    private String nombre;
   @NotBlank(message = "el apellido no puedo estar en blanco")
    private String apellido;
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Enumerated(EnumType.STRING)
    private Genero genero;

}
