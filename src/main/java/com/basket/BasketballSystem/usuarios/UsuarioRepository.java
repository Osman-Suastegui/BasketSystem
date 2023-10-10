package com.basket.BasketballSystem.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {


    Optional<Object> findByEmail(String email);


    List<Usuario> findByUsuarioContainingAndRol(String usuario, Rol rol);


    Optional<Usuario> findByUsuario(String usuario);


}
