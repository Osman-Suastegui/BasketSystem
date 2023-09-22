package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PartidoService {
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    public List<Map<String,Object>>  obtenerPartidosArbitro(String idArbitro) {
        Usuario arbitro = usuarioRepository.findById(idArbitro).orElse(null);
        if(arbitro == null) throw new BadRequestException("El arbitro no existe");

         List<Partido> partidos =  partidoRepository.findAllByArbitro(arbitro);
         List<Partido> partidosFiltrados = partidos.stream().filter(partido -> partido.getGanador().isEmpty()).
             collect(Collectors.toList());

        partidosFiltrados.sort((partido1, partido2) -> {
            Timestamp fechaInicio1 = partido1.getFechaInicio();
            Timestamp fechaInicio2 = partido2.getFechaInicio();
            return fechaInicio1.compareTo(fechaInicio2);
        });



        List<Map<String,Object>> partidosMap = new ArrayList<>();

        for(Partido partido: partidosFiltrados){
            Map<String,Object> p = new HashMap<>();

            p.put("idPartido",partido.getClavePartido());
            p.put("arbitro",arbitro.getUsuario());
            p.put("fechaInicio",partido.getFechaInicio());
            p.put("temporadaId",partido.getTemporada().getClaveTemporada());
            p.put("equipo1",partido.getEquipo1().getNombre());
            p.put("equipo2",partido.getEquipo2().getNombre());
            partidosMap.add(p);
        }



         return partidosMap;
    }


}
