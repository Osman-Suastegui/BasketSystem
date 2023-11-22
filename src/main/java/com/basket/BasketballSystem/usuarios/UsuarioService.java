package com.basket.BasketballSystem.usuarios;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.usuarios.DTO.ActualizarUsuarioRequest;
import com.basket.BasketballSystem.usuarios.DTO.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

        @Autowired
        private UsuarioRepository usuarioRepository;

        public List<Usuario> getAllUsuarios() {

            return usuarioRepository.findAll();
        }


    public ResponseEntity<Map<String, Object>> actualizarUsuario(ActualizarUsuarioRequest req  ) {
        // Verifica si el usuario existe por su ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(req.getUsuario());
        if (usuarioOptional.isEmpty()) {
            throw new BadRequestException("El usuario no existe.");
        }
        Usuario usuario = usuarioOptional.get();
        usuario.setNombre(req.getNombre());
        usuario.setApellido(req.getApellido());
        usuarioRepository.save(usuario);

        Map<String, Object> userAct = new HashMap<>();
        userAct.put("message", "Nombre y apellido del usuario actualizados exitosamente.");

        return ResponseEntity.ok(userAct);
    }


    public List<UsuarioResponse> buscarUsuariosPorLetrasEnNombre(String usuario, Rol rol) {
            if (rol == null ){
                rol = Rol.JUGADOR;
            }
        List<Usuario> usuariosBD = usuarioRepository.findByUsuarioContainingAndRol(usuario, rol);
        List<UsuarioResponse> usuariosResponse = new ArrayList<>();
        for (Usuario usuarioBD : usuariosBD) {
            UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                    .usuario(usuarioBD.getUsuario())
                    .nombre(usuarioBD.getNombre())
                    .apellido(usuarioBD.getApellido())
                    .email(usuarioBD.getEmail())
                    .fechaNacimiento(usuarioBD.getFechaNacimiento())
                    .genero(usuarioBD.getGenero())
                    .rol(usuarioBD.getRol())
                    .build();
            usuariosResponse.add(usuarioResponse);
        }
        return usuariosResponse;
    }

    public ResponseEntity<String> obtenerTipoUser(String usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("El usuario no existe.");
        }
        Rol userRol = usuarioOptional.get().getRol();
        return ResponseEntity.ok(userRol.toString());
    }


    public Usuario obtenerUsuario(String usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario);
        if (usuarioOptional.isEmpty()) {
            throw new BadRequestException("El usuario no existe.");
        }
        return usuarioOptional.get();
    }



}



