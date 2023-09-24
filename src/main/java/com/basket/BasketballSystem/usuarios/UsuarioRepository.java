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


    @Query("SELECT u FROM Usuario u WHERE u.usuario LIKE %:usuario%")
    List<Usuario> findByUsuarioContaining(@Param("usuario") String usuario);

    Usuario findByUsuario(String usuario);

}
