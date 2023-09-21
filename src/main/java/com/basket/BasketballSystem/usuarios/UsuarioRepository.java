package com.basket.BasketballSystem.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Object> findByUsuario(String usuario);

    Optional<Object> findByEmail(String email);




}
