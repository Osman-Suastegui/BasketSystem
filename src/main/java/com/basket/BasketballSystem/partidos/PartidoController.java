package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.partidos.DTO.PartidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Partido")
public class PartidoController {

    @Autowired
    PartidoService partidoService;

    @RequestMapping ("/obtenerPartidosArbitro")
    public List<Map<String,Object>>  obtenerPartidosArbitro(@RequestParam("idArbitro") String idArbitro, @RequestParam(value = "estatusPartido",required = false) String estatusPartido){
        return partidoService.obtenerPartidosArbitro(idArbitro,estatusPartido);
    }


    @RequestMapping ("/obtenerPartidosEquipo")
    public List<Map<String,Object>>  obtenerPartidosEquipo(@RequestParam("idEquipo") String idEquipo, @RequestParam(value = "estatusPartido",required = false) String estatusPartido ){
        return partidoService.obtenerPartidosEquipo(idEquipo,estatusPartido);
    }


    @RequestMapping ("/obtenerPartidosJugador")
    public List<Map<String,Object>>  obtenerPartidosJugador(@RequestParam("idJugador") String idJugador){

        return partidoService.obtenerPartidosJugador(idJugador);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @RequestMapping ("/obtenerPartidosTemporada")
    public List<Map<String,Object>>  obtenerPartidosTemporada(@RequestParam("idTemporada") Long idTemporada){

        return partidoService.obtenerPartidosTemporada(idTemporada);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PutMapping("/agendar")
    public ResponseEntity<Map<String, Object>> agendarPartido(@RequestBody Map<String, Object> partido){
        Long idPartido = Long.parseLong(partido.get("clavePartido").toString());
        String fecha = partido.get("fechaInicio").toString();
        return partidoService.agendarPartido(idPartido,fecha);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PostMapping("/generarPartidosTemporada")
    public ResponseEntity<Map<String, Object>> generarPartidosTemporada(@RequestBody Map<String, Object> temporada) {
        Integer idTemporada = (Integer) temporada.get("idTemporada");
        Long idTemporadaLong = idTemporada.longValue();
        return partidoService.generarPartidosTemporada(idTemporadaLong);
    }



    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PutMapping("/asignarArbitro")
        public ResponseEntity<Map<String, Object>> asignarArbitro(@RequestBody Partido partido){
        Long idPartido = partido.getClavePartido();
        String idArbitro = partido.getArbitro().getUsuario();
        return partidoService.asignarArbitro(idPartido,idArbitro);

    }

    @GetMapping("/obtenerPartido")
    public ResponseEntity<PartidoResponse> obtenerPartido(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerPartido(idPartido);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @GetMapping("/obtenerEquipo1Equipo2")
    public ResponseEntity<Map<String, Object>> obtenerEquipo1Equipo2(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerEquipo1Equipo2(idPartido);
    }

    @PostMapping("/crearPartidosTemporadaRegular")

    public ResponseEntity<Map<String, Object>> generarPartidosTemporadaRegular(@RequestBody Map<String, Object> temporada){
        Integer idTemporada = (Integer) temporada.get("idTemporada");
        int cantidadEnfrentamientosRegular = (Integer) temporada.get("cantidadEnfrentamientosRegular");
        Long idTemporadaLong = idTemporada.longValue();
        return partidoService.crearPartidosTemporadaRegular(idTemporadaLong,cantidadEnfrentamientosRegular);
    }


    @GetMapping("/rankingTemporadaRegular")
    public ResponseEntity<Map<String, Integer>> rankingTemporadaRegular(@RequestParam("idTemporada") Long idTemporada){
        Map<String, Integer> ranking = partidoService.rankingTemporadaRegular(idTemporada);
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }

//    solo para tesst
    @PutMapping("/setGanadorAleatorio")
    public ResponseEntity<Map<String, Object>> setGanadorAleatorio(@RequestBody Map<String, Object> partido){
        Long idPartido = Long.parseLong(partido.get("idTemporada").toString());
        partidoService.setGanadorAleatorio(idPartido);
        return ResponseEntity.ok(Map.of("mensaje", "Ganador asignado"));
    }

    @PostMapping("/crearPartidosEliminatorias")
    public ResponseEntity<Map<String, Object>>  crearPartidosEliminatorias(@RequestBody Map<String, Object> idTemporadaBody){
        Long idTemporada = Long.parseLong(idTemporadaBody.get("idTemporada").toString());
        partidoService.crearPartidosEliminatorias(idTemporada);
        return ResponseEntity.ok(Map.of("mensaje", "Partidos creados"));
    }

//    fechaInicio
    @GetMapping("/obtenerFechaInicio")
    public ResponseEntity<Map<String, Object>> obtenerFechaInicio(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerFechaInicio(idPartido);
    }






}
