package com.basket.BasketballSystem.usuarios;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.exceptions.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

        @Autowired
        private UsuarioRepository usuarioRepository;
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
}
