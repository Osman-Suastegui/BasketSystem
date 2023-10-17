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
    @NotBlank(message = "el usuario no puede estar en blanco")

    private String usuario;
    @NotBlank(message = "el nombre no puede estar en blanco")

    private String nombre;
    @NotBlank(message = "el apellido no puede estar en blanco")
    private String apellido;


}
