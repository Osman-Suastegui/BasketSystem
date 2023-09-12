package com.basket.BasketballSystem.data;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.exceptions.GlobalExceptionHandler;
import com.basket.BasketballSystem.usuarios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    UsuarioService usuarioService;



    @Override
    public void run(String... args) throws Exception {

        initializeUsuarios();
    }

    private void initializeUsuarios(){
        Usuario usuario1 = new Usuario(
                "usuario1",
                "usuario1@example.com",
                "contrasena1",
                "Nombre1",
                LocalDate.of(1990, 5, 15),
                "Apellido1",
                Genero.MASCULINO,
                Rol.ADMIN_EQUIPO
        );
        Usuario usuario2 = new Usuario(
                "Jesus123",
                "jesus@hotmail.com",
                "clave1",
                "Jesus",
                LocalDate.of(1990, 1, 15),
                "sanchez",
                Genero.MASCULINO,
                Rol.ADMIN_EQUIPO
        );
        Usuario usuario3 = new Usuario(
                "Manuel321",
                "manuel@hotmail.com",
                "clave2",
                "Manuel",
                LocalDate.of(1985, 3, 22),
                "lopez",
                Genero.MASCULINO,
                Rol.ADMIN_EQUIPO
        );
        List<Usuario> usuarios = List.of(usuario1, usuario2, usuario3);
        usuarios.forEach(usuario -> {
            try {
                usuarioService.addNewUsuario(usuario);
            } catch (BadRequestException e) {
                System.out.println(e.getMessage());
            }
        });




    }
}
