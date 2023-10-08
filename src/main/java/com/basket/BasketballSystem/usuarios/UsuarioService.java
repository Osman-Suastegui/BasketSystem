package com.basket.BasketballSystem.usuarios;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.exceptions.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<String> registrarUsuario(Usuario usuario) {

        // Verifica si el correo electrónico ya existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }
        if (usuario.getFechaNacimiento() == null)
            return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser nula.");

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario creado exitosamente.");
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


    public List<Usuario> buscarUsuariosPorLetrasEnNombre(String usuario, Rol rol) {
            if (rol == null ){
                rol = Rol.JUGADOR;
            }
        return usuarioRepository.findByUsuarioContainingAndRol(usuario, rol);
    }

}



