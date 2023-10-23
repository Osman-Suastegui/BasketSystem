package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.ligas.DTO.obtenerLigasDeAdminResponse;
import com.basket.BasketballSystem.temporadas.DTO.obtenerTemporadasDeLigaResponse;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LigaService {

    LigaRepository ligaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    public LigaService(LigaRepository ligaRepository) {
        this.ligaRepository = ligaRepository;
    }

    public List<Temporada> obtenerTemporadas(Long idLiga) {
        if (idLiga == null) throw new BadRequestException("El id de la liga no puede ser nulo");
        Optional<Liga> liga = ligaRepository.findById(idLiga);
        if (!liga.isPresent()) throw new BadRequestException("La liga " + idLiga + " no existe");

        return liga.get().getTemporadas();

    }

    public List<Usuario> obtenerAdministradores(Long idLiga) {
        if (idLiga == null) throw new BadRequestException("El id de la liga no puede ser nulo");
        Optional<Liga> liga = ligaRepository.findById(idLiga);
        if (!liga.isPresent()) throw new BadRequestException("La liga " + idLiga + " no existe");


        liga.get().getAdministradores().forEach(usuario -> usuario.setPassword(null));
        return liga.get().getAdministradores();
    }

    public ResponseEntity<Map<String, Object>> asignarAdministrador(Long ligaId, String usuarioId) {
        Optional<Liga> ligaOptional = ligaRepository.findById(ligaId);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (!ligaOptional.isPresent()) {
            throw new BadRequestException("La liga " + ligaId + " no existe");
        }
        if (!usuarioOptional.isPresent()) {
            throw new BadRequestException("El usuario " + usuarioId + " no existe");
        }

        Liga liga = ligaOptional.get();
        Usuario usuario = usuarioOptional.get();
        liga.getAdministradores().add(usuario);
        ligaRepository.save(liga);

        Map<String, Object> ligaAdmin = new HashMap<>();

        ligaAdmin.put("message", "Liga creada exitosamente.");

        return ResponseEntity.ok(ligaAdmin);
    }


    public ResponseEntity<Map<String, Object>> crearLiga(Liga liga) {
        // agregame validaciones
        if (liga.getNombre() == null) throw new BadRequestException("El nombre de la liga no puede ser nulo");
        if (liga.getNombre().isEmpty()) throw new BadRequestException("El nombre de la liga no puede estar vacio");
        if (liga.getNombre().length() > 50)
            throw new BadRequestException("El nombre de la liga no puede tener mas de 50 caracteres");
        if (ligaRepository.findByNombre(liga.getNombre()).isPresent())
            throw new BadRequestException("Ya existe una liga con ese nombre");
        if (liga.getNombre().length() < 3)
            throw new BadRequestException("El nombre de la liga debe tener al menos 3 caracteres");


        ligaRepository.save(liga);
        Map<String, Object> ligaMap = new HashMap<>();

        ligaMap.put("message", "Liga creada exitosamente.");
        ligaMap.put("idLiga", liga.getId());
        return ResponseEntity.ok(ligaMap);
    }


    //quiero hacer un update al nombre de la liga
    public ResponseEntity<Map<String, Object>>  actualizarLiga(Long ligaId, String nombre) {
        Optional<Liga> ligaOptional = ligaRepository.findById(ligaId);
        if (!ligaOptional.isPresent()) {
            throw new BadRequestException("La liga " + ligaId + " no existe");
        }
        Liga liga = ligaOptional.get();
        liga.setNombre(nombre);
        if (liga.getNombre() == null) throw new BadRequestException("El nombre de la liga no puede ser nulo");
        if (liga.getNombre().isEmpty()) throw new BadRequestException("El nombre de la liga no puede estar vacio");
        if (liga.getNombre().length() > 50)
            throw new BadRequestException("El nombre de la liga no puede tener mas de 50 caracteres");
        if (ligaRepository.findByNombre(liga.getNombre()).isPresent())
            throw new BadRequestException("Ya existe una liga con ese nombre");
        if (liga.getNombre().length() < 3)
            throw new BadRequestException("El nombre de la liga debe tener al menos 3 caracteres");

        ligaRepository.save(liga);


        Map<String, Object> ligaName = new HashMap<>();

        ligaName.put("message", "Nombre de liga modificado exitosamente.");
        return ResponseEntity.ok(ligaName);



    }

    public List<Map<String, Object>> buscarLigaPorNombre(String nombre) {

        List<Liga> ligas = ligaRepository.findByNombreContaining(nombre);

        List<Map<String, Object>> ligasMap = new ArrayList<>();


        for (Liga liga : ligas) {
            Map<String, Object> l = new HashMap<>();

            l.put("idLiga", liga.getId());
            l.put("nombre", liga.getNombre());
            ligasMap.add(l);
        }


        return ligasMap;
    }

    public List<obtenerLigasDeAdminResponse> ObtenerLigasDeAdmin(String usuario) {
        if (usuario == null) {
            throw new BadRequestException("El usuario no puede ser nulo");
        }

        List<Object[]> LigasAdmin = ligaRepository.findLigaInfoForUser(usuario);

        List<obtenerLigasDeAdminResponse> ligasAdminRes = new ArrayList<>();

        for (Object[] Liga: LigasAdmin) {
            obtenerLigasDeAdminResponse LigasResponse = new obtenerLigasDeAdminResponse();

            LigasResponse.setIdLiga((Long) Liga[0]);
            LigasResponse.setNombreLiga((String) Liga[1]);
            ligasAdminRes.add(LigasResponse);
        }

        // Llama a la consulta personalizada para obtener las ligas del administrador
        return ligasAdminRes;
    }


    public List<String> obtenerLigasNoAdmin(Long idLiga) {
        if (idLiga == null) throw new BadRequestException("El id de la liga no puede ser nulo");
        Optional<Liga> liga = ligaRepository.findById(idLiga);
        if (!liga.isPresent()) throw new BadRequestException("La liga " + idLiga + " no existe");

        return ligaRepository.findAdminLigaNotInLiga(idLiga);
    }

    public List<String> obtenerAdminsDeLiga(Long idLiga) {
        if (idLiga == null) throw new BadRequestException("El id de la liga no puede ser nulo");
        Optional<Liga> liga = ligaRepository.findById(idLiga);
        if (!liga.isPresent()) throw new BadRequestException("La liga " + idLiga + " no existe");

        return ligaRepository.findUsuariosByLigaId(idLiga);
    }
}



