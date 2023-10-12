package com.basket.BasketballSystem.usuarios.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//String usuarioId, String nuevoNombre, String nuevoApellido
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarUsuarioRequest {
    @NotEmpty(message = "el usuario no puede estar vacio") @NotNull(message = "el usuario no puede ser nulo") @NotBlank(message = "el usuario no puede estar en blanco")

    private String usuario;
    @NotEmpty(message = "el nombre no puede estar vacio") @NotNull(message = "el nombre no puede ser nulo") @ NotBlank(message = "el nombre no puede estar en blanco")

    private String nombre;
    @NotEmpty(message = "el apellido no puede estar vacio") @NotNull(message = "el apellido no puede ser nulo") @ NotBlank(message = "el apellido no puede estar en blanco")
    private String apellido;


}
