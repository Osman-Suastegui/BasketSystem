package com.basket.BasketballSystem.usuarios;


import com.basket.BasketballSystem.usuarios.DTO.ActualizarUsuarioRequest;
import com.basket.BasketballSystem.usuarios.DTO.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping("/getAllUsuarios")
    @PreAuthorize("hasAnyRole('ROLE_JUGADOR', 'ROLE_ADMIN_EQUIPO', 'ROLE_ADMIN_LIGA', 'ROLE_ARBITRO')")
    public List<Usuario> getAllUsuarios() {

        return usuarioService.getAllUsuarios();
    }


    @PutMapping("ActualizarUsuario")
    @PreAuthorize("hasAnyRole('ROLE_JUGADOR', 'ROLE_ADMIN_EQUIPO', 'ROLE_ADMIN_LIGA', 'ROLE_ARBITRO')")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@Validated @RequestBody ActualizarUsuarioRequest req) {
        return usuarioService.actualizarUsuario(req);
    }

    @GetMapping("obtenerUsuario")
    public Usuario obtenerUsuario(@RequestParam(name = "usuario", required = false) String usuario) {
        System.out.println("usuario " + usuario );
        return usuarioService.obtenerUsuario(usuario);
    }



    @GetMapping("/obtenerJugador")
    public List<UsuarioResponse> obtenerJugador(
            @RequestParam(name = "usuario", required = false) String usuario
    ) {
        return usuarioService.buscarUsuariosPorLetrasEnNombre(usuario);
    }

    @GetMapping("/obtenerUsuarioPorUser")
    public Usuario obtenerUsuarioPorUser(@RequestParam(name = "usuario", required = false) String usuario) {
        return usuarioService.obtenerUsuarioPorUser(usuario);
    }










}
