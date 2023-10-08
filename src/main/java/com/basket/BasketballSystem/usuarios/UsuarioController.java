package com.basket.BasketballSystem.usuarios;


import com.basket.BasketballSystem.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping()
    public List<Usuario> getAllUsuarios() {

        return usuarioService.getAllUsuarios();
    }

    @PostMapping("RegistrarUsuario")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Validated Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PutMapping("ActualizarUsuario")
    public ResponseEntity<String> actualizarUsuario(@RequestBody Usuario usuario) {
        String usuarioId = usuario.getUsuario(); // Obtener el ID del usuario
        String nuevoNombre = usuario.getNombre(); // Obtener el nuevo nombre
        String nuevoApellido = usuario.getApellido(); // Obtener el nuevo apellido

        return usuarioService.actualizarUsuario(usuarioId, nuevoNombre, nuevoApellido);
    }



    @GetMapping("/obtenerJugador")
    public List<Usuario> obtenerJugador(
            @RequestParam(name = "usuario", required = false) String usuario,
            @RequestParam(name = "rol", required = false) Rol rol
    ) {
        return usuarioService.buscarUsuariosPorLetrasEnNombre(usuario, rol);
    }






}
