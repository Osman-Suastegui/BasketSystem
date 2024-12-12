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
        usuario.setName(req.getNombre());
        usuario.setLastName(req.getApellido());
        usuarioRepository.save(usuario);

        Map<String, Object> userAct = new HashMap<>();
        userAct.put("message", "Nombre y apellido del usuario actualizados exitosamente.");

        return ResponseEntity.ok(userAct);
    }


    public List<UsuarioResponse> buscarUsuariosPorLetrasEnNombre(String usuario) {
        List<Usuario> usuariosBD = usuarioRepository.findByUsuarioContaining(usuario);
        List<UsuarioResponse> usuariosResponse = new ArrayList<>();
        for (Usuario usuarioBD : usuariosBD) {
            UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                    .usuario(usuarioBD.getUsuario())
                    .build();
            usuariosResponse.add(usuarioResponse);
        }
        return usuariosResponse;
    }




    public Usuario obtenerUsuario(String usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario);
        if (usuarioOptional.isEmpty()) {
            throw new BadRequestException("El usuario no existe.");
        }
        return usuarioOptional.get();
    }


    public Usuario obtenerUsuarioPorUser(String usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuario(usuario);
        if (usuarioOptional.isEmpty()) {
            throw new BadRequestException("El usuario no existe.");
        }
        return usuarioOptional.get();
    }

}



