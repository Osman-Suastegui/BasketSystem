package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaService;
import com.basket.BasketballSystem.temporadas.DTO.TemporadaRequest;
import com.basket.BasketballSystem.temporadas.DTO.obtenerTemporadasDeLigaResponse;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
// CHANGE NAME TO TOURNAMENT
@RestController
@RequestMapping("/Temporadas")
public class TemporadaController {

    @Autowired
    TemporadaService TemporadaService;

    @PostMapping("/crearTemporada")
    public ResponseEntity<Map<String, Object>> crearTemporada(@RequestBody Temporada temporada) {


        return TemporadaService.crearTemporada(temporada);
    }

    @PutMapping("/asignarLiga")
    public ResponseEntity<Map<String, Object>> asignarLiga(@RequestBody Map<String, Object> requestMap) {
        Long ligaId = Long.parseLong(requestMap.get("ligaId").toString());
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());

        return TemporadaService.asignarLiga(temporadaId, ligaId);

    }

    @PutMapping("/modificarDatosTemporada")
    public ResponseEntity<String> modificarDatosTemporada(@RequestBody Map<String, Object> requestMap) {
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
        Estado estado = Estado.valueOf(requestMap.get("estado").toString());

        return TemporadaService.modificarDatosTemporada(temporadaId, estado);
    }


    @DeleteMapping("/eliminarArbitro")
    public ResponseEntity<Map<String, Object>> eliminarArbitro(
            @RequestParam("temporadaId") Long temporadaId,
            @RequestParam("arbitroId") String arbitroId) {
        return TemporadaService.eliminarArbitroDeTemporada(temporadaId, arbitroId);
    }


    @GetMapping("/obtenerArbitrosNoEnTemporada")
    public List<String> obtenerArbitrosNoEnTemporada(@RequestParam(name = "idTemporada",required = false) Long temporadaId) {
        return TemporadaService.obtenerArbitrosNoEnTemporada(temporadaId);
    }


    @PostMapping("/agregarArbitro")
    public ResponseEntity<Map<String, Object>> agregarArbitro(@RequestBody Map<String, Object> requestMap) {
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
        String arbitroId = requestMap.get("arbitroId").toString();
        return TemporadaService.agregarArbitro(temporadaId, arbitroId);
    }

    @GetMapping("/obtenerArbitros")
    public List<Usuario> obtenerArbitros(@RequestParam(name = "idTemporada",required = false) Long temporadaId) {
        return TemporadaService.obtenerArbitros(temporadaId);
    }

    @GetMapping("/buscarTemporadasPorNombre")
        public List<Map<String,Object>> buscarTemporadasPorNombre(@RequestParam(name = "nombreTemporada",required = false) String nombreTemporada) {
        return TemporadaService.buscarTemporadasPorNombre(nombreTemporada);
    }

    @GetMapping("/obtenerTemporadasDeLiga")
    public List<obtenerTemporadasDeLigaResponse> obtenerTemporadasDeLiga(@RequestParam(name = "idLiga", required = false) Long idLiga){
        return TemporadaService.obtenerTemporadasDeLiga(idLiga);
    }

    @GetMapping("/obtenerEstadoTemporada")
    public ResponseEntity<Map<String, Object>> obtenerEstadoTemporada(@RequestParam(name = "idTemporada", required = false) Long idTemporada){
        return TemporadaService.obtenerEstadoTemporada(idTemporada);
    }

    @PutMapping("/modificarCaracteristicasTemporada")
    public ResponseEntity<Map<String, Object>> modificarCaracteristicasTemporada(@RequestBody TemporadaRequest request) {
        return TemporadaService.modificarCaracteristicasTemporada(request);
    }

    @GetMapping("/obtenerCaracteristicasTemporada")
    public ResponseEntity<Map<String, Object>> obtenerCaracteristicasTemporada(@RequestParam(name = "idTemporada", required = false) Long idTemporada){
        return TemporadaService.obtenerCaracteristicasTemporada(idTemporada);
    }

}
