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
        // Verifica si el usuario ya existe por nombre de usuario
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }

        // Verifica si el correo electrónico ya existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
        }

        // Resto de las validaciones
        if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty())
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo o vacío.");
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty())
            return ResponseEntity.badRequest().body("El nombre no puede ser nulo o vacío.");
        if (usuario.getApellido() == null || usuario.getApellido().isEmpty())
            return ResponseEntity.badRequest().body("El apellido no puede ser nulo o vacío.");
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty())
            return ResponseEntity.badRequest().body("El correo electrónico no puede ser nulo o vacío.");
        if (usuario.getFechaNacimiento() == null)
            return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser nula.");
        if (usuario.getGenero() == null)
            return ResponseEntity.badRequest().body("El género no puede ser nulo.");
        if (usuario.getRol() == null)
            return ResponseEntity.badRequest().body("El rol no puede ser nulo.");
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty())
            return ResponseEntity.badRequest().body("La contraseña no puede ser nula o vacía.");

        // Hashear la contraseña antes de guardarla en la base de datos
        // String hashedPassword = passwordEncoder.encode(usuario.getPassword());
       // usuario.setPassword(hashedPassword);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario creado exitosamente.");
    }



}
