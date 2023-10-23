package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.ligas.DTO.obtenerLigasDeAdminResponse;
import com.basket.BasketballSystem.temporadas.DTO.obtenerTemporadasDeLigaResponse;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PostMapping("/asignarAdmin")
    public ResponseEntity<Map<String, Object>> asignarAdmin(@RequestBody Map<String, Object> requestMap) {
        Long ligaId = Long.parseLong(requestMap.get("ligaId").toString());
        String usuarioId = (String) requestMap.get("usuarioId");

        return ligaService.asignarAdministrador(ligaId, usuarioId);
    }



    @PostMapping("/crearLiga")
    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    public ResponseEntity<Map<String,Object>> crearLiga(@RequestBody Liga liga) {

        return ligaService.crearLiga(liga);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PutMapping("/actualizarLiga")
    public ResponseEntity<Map<String, Object>>  actualizarLiga(@RequestBody Map<String, Object> requestMap) {
        Long ligaId = Long.parseLong(requestMap.get("ligaId").toString());
        String nombre = (String) requestMap.get("nombre");

        return ligaService.actualizarLiga(ligaId, nombre);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @GetMapping("/obtenerAdminsNoEnLiga")
    public List<String>obtenerLigasNoAdmin(@RequestParam(name = "idLiga", required = false) Long idLiga){
        return ligaService.obtenerLigasNoAdmin(idLiga);
    }



    @GetMapping("/buscarLigaPorNombre")
    public List<Map<String,Object>> buscarLigaPorNombre(@RequestParam(name = "nombre",required = false) String nombre) {
        return ligaService.buscarLigaPorNombre(nombre);
    }

    //obtener todos las ligas de un usuario
    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @GetMapping("/obtenerLigasDeAdmin")
    public List<obtenerLigasDeAdminResponse> ObtenerLigasDeAdmin(@RequestParam(name = "usuario", required = false) String usuario){
        return ligaService.ObtenerLigasDeAdmin(usuario);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @GetMapping("/obtenerAdminsDeLiga")
    public List<String> obtenerAdminsDeLiga(@RequestParam(name = "idLiga", required = false) Long idLiga){
        return ligaService.obtenerAdminsDeLiga(idLiga);
    }




}
