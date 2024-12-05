package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadoresEquipoRepository extends JpaRepository<JugadoresEquipo, String> {

    @Modifying
    @Query("DELETE FROM JugadoresEquipo e WHERE e.equipo.nombre = :param1 AND e.jugador.usuario = :param2")
    void deleteByJugadorAndEquipo(@Param("param1") String param1, @Param("param2") String param2);


    List<JugadoresEquipo> findAllByJugador(Usuario jugador);

//que sea por el nombre del equipo
    List<JugadoresEquipo> findAllByEquipo(Equipo equipo);


    JugadoresEquipo findByJugadorAndEquipo_Nombre(Usuario jugador, String nombreEquipo);

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.rol = 'jugador' " +
            "AND u.usuario NOT IN (" +
            "    SELECT je.jugador.usuario FROM JugadoresEquipo je WHERE je.equipo.nombre = :equipoNombre" +
            ")")
    default List<Usuario> findJugadoresNotInEquipoWithAgeAndGenderCondition(
            // Asegúrate de utilizar el tipo enumerado correcto
            @Param("equipoNombre") String equipoNombre
    ) {
        return null;
    }


    @Query(value= "SELECT je.jugador FROM jugadores_equipos je " +
            "WHERE je.nombre_equipo = ?1 " +
            "AND je.jugador NOT IN " +
            "(SELECT jp.jugador FROM jugadores_partidos jp " +
            "WHERE jp.clave_partido = ?2 AND (jp.equipo = ?1 OR jp.equipo = ?3))", nativeQuery = true)
    List<String> findJugadoresNoEnPartidos(String nombreEquipo, Long clavePartido, String otroEquipo);





}
