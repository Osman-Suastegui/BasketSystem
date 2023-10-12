package com.basket.BasketballSystem.usuarios;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.exceptions.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

        @Autowired
        private UsuarioRepository usuarioRepository;

      //  @Autowired
    //    private PasswordEncoder passwordEncoder;

//        @Autowired
//        GlobalExceptionHandler globalExceptionHandler;


        public List<Usuario> getAllUsuarios() {

            return usuarioRepository.findAll();
        }

        public void addNewUsuario(Usuario nuevoUsuario) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(nuevoUsuario.getUsuario());
            if(usuarioOptional.isPresent()){
                throw new BadRequestException("El usuario " + nuevoUsuario.getUsuario() + " ya existe");
            }
            usuarioRepository.save(nuevoUsuario);
        }


    public ResponseEntity<String> actualizarUsuario(String usuarioId, String nuevoNombre, String nuevoApellido) {
        // Verifica si el usuario existe por su ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("El usuario no existe.");
        }

        // Obtiene el usuario de la base de datos
        Usuario usuario = usuarioOptional.get();

        // Actualiza el nombre y el apellido si se proporcionan valores válidos
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            usuario.setNombre(nuevoNombre);
        }
        if (nuevoApellido != null && !nuevoApellido.isEmpty()) {
            usuario.setApellido(nuevoApellido);
        }



        // Guarda los cambios en la base de datos
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Nombre y apellido del usuario actualizados exitosamente.");
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

}



