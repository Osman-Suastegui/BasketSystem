package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Ligas")
public class LigaController {

    @Autowired
    LigaService ligaService;
    @RequestMapping("/obtenerTemporadas")
    public List<Temporada> obtenerTemporadas(@RequestParam(name = "idLiga",required = false) Long idLiga) {

        return ligaService.obtenerTemporadas(idLiga);
    }

    @RequestMapping("/obtenerAdministradores")
    public List<Usuario> obtenerAdministradores(@RequestParam(name = "idLiga",required = false) Long idLiga) {

        return ligaService.obtenerAdministradores(idLiga);
    }

}
