package com.basket.BasketballSystem.partidos;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping ("/obtenerPartidosJugador")
    public List<Map<String,Object>>  obtenerPartidosJugador(@RequestParam("idJugador") String idJugador){

        return partidoService.obtenerPartidosJugador(idJugador);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @RequestMapping ("/obtenerPartidosTemporada")
    public List<Map<String,Object>>  obtenerPartidosTemporada(@RequestParam("idTemporada") Long idTemporada){

        return partidoService.obtenerPartidosTemporada(idTemporada);
    }

    @PutMapping("/agendar")
    public ResponseEntity<Map<String, Object>> agendarPartido(@RequestBody Partido partido){
        Long idPartido = partido.getClavePartido();
        String fecha = partido.getFechaInicio().toString();
        System.out.println("Fecha recibida en el controlador: " + fecha);
        return partidoService.agendarPartido(idPartido,fecha);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PostMapping("/generarPartidosTemporada")
    public ResponseEntity<Map<String, Object>> generarPartidosTemporada(@RequestBody Map<String, Object> temporada) {
        Integer idTemporada = (Integer) temporada.get("idTemporada");
        Long idTemporadaLong = idTemporada.longValue();
        return partidoService.generarPartidosTemporada(idTemporadaLong);
    }




    @PutMapping("/asignarArbitro")
        public ResponseEntity<Map<String, Object>> asignarArbitro(@RequestBody Partido partido){
        Long idPartido = partido.getClavePartido();
        String idArbitro = partido.getArbitro().getUsuario();
        return partidoService.asignarArbitro(idPartido,idArbitro);

    }





}
