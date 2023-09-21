package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaService;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Temporadas")
public class TemporadaController {



    @Autowired
    TemporadaService TemporadaService;

    @PostMapping("/crearTemporada")
    public ResponseEntity<String> crearTemporada(@RequestBody Temporada temporada) {


        return TemporadaService.crearTemporada(temporada);
    }
    @PutMapping("/asignarLiga")
    public ResponseEntity<String> asignarLiga(@RequestBody Map<String, Object> requestMap) {
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

    @PostMapping("/agregarArbitro")
    public ResponseEntity<String> agregarArbitro(@RequestBody Map<String, Object> requestMap) {
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
        String arbitroId = requestMap.get("arbitroId").toString();
        return TemporadaService.agregarArbitro(temporadaId, arbitroId);
    }
    @GetMapping("/obtenerArbitros")
    public List<Usuario> obtenerArbitros(@RequestParam(name = "idTemporada",required = false) Long temporadaId) {
        return TemporadaService.obtenerArbitros(temporadaId);
    }


}
