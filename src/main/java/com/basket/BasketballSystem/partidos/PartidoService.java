package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.equipos_temporadas.EquipoTemporadaRepository;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.temporadas.TemporadaRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartidoService {
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;
    @Autowired
    TemporadaRepository temporadaRepository;
    @Autowired
    EquipoTemporadaRepository equipoTemporadaRepository;


    public List<Map<String, Object>> obtenerPartidosArbitro(String idArbitro, String estatusPartido) {
        final int duracionPartido = 40; // 40 minutos dura un partido ??
//        ESTATUS PARTIDO enCurso, finalizado, proximo
        List<Map<String, Object>> partidosFinalizadosMap = new ArrayList<>();
        List<Map<String, Object>> partidosEnCursoMap = new ArrayList<>();
        List<Map<String, Object>> partidosProximosMap = new ArrayList<>();
        // Obtén la fecha y hora actual
        Instant horaAcutal = Instant.now();

        Duration durationPartido = Duration.ofMinutes(duracionPartido);
        Instant horaActualMasDurationPartido = horaAcutal.plus(durationPartido);
        System.out.println("HORA ACTUAL " + horaAcutal);
        System.out.println("HORA ACTUAL MAS DURATION " + horaActualMasDurationPartido);


        Usuario arbitro = usuarioRepository.findById(idArbitro).orElse(null);
        if (arbitro == null) throw new BadRequestException("El arbitro no existe");

        List<Partido> partidos = partidoRepository.findAllByArbitro(arbitro);
        List<Partido> partidosFiltrados = partidos.stream().filter(partido -> partido.getGanador().isEmpty() && partido.getFechaInicio() != null ).
                collect(Collectors.toList());

        partidosFiltrados.sort((partido1, partido2) -> {
            Instant fechaInicio1 = partido1.getFechaInicio();
            Instant fechaInicio2 = partido2.getFechaInicio();
            return fechaInicio1.compareTo(fechaInicio2);
        });


        List<Map<String, Object>> partidosMap = new ArrayList<>();

        for (Partido partido : partidosFiltrados) {
            Map<String, Object> p = new HashMap<>();
            p.put("idPartido", partido.getClavePartido());
            p.put("arbitro", arbitro.getUsuario());
            p.put("fechaInicio", partido.getFechaInicio());
            Instant fechaInicioPartido = partido.getFechaInicio();
            Instant fechaEndPartido = fechaInicioPartido.plus(durationPartido);

            p.put("temporadaId", partido.getTemporada().getClaveTemporada());
            p.put("equipo1", partido.getEquipo1().getNombre());
            p.put("equipo2", partido.getEquipo2().getNombre());

            if (fechaInicioPartido.isBefore(horaAcutal) && fechaEndPartido.isAfter(horaAcutal)){
                partidosEnCursoMap.add(p);
            }
            else if (fechaInicioPartido.isAfter(horaAcutal)) {
                partidosProximosMap.add(p);
            }
            else{
                partidosFinalizadosMap.add(p);

            }



        }

        if (estatusPartido.equals("enCurso")){
            System.out.println("EN CURSO");
            return partidosEnCursoMap;
        }else if(estatusPartido.equals("finalizados")){
            System.out.println("FINALIZADOS");
            return partidosFinalizadosMap;
        }else if (estatusPartido.equals("proximos")){
            System.out.println("PROXIMOS");
            return partidosProximosMap;
        }

//        retornamos todos los partidos
        partidosMap.addAll(partidosEnCursoMap);
        partidosMap.addAll(partidosFinalizadosMap);
        partidosMap.addAll(partidosProximosMap);
        return partidosMap;
    }


    public List<Map<String, Object>> obtenerPartidosJugador(String idJugador) {
        Usuario jugador = usuarioRepository.findById(idJugador).orElse(null);
        if (jugador == null) {
            throw new BadRequestException("El jugador no existe");
        }

        List<JugadoresEquipo> jugadoresEquipoTmp = jugadoresEquipoRepository.findAllByJugador(jugador);
        List<Equipo> equipos = new ArrayList<>();
        jugadoresEquipoTmp.forEach(je -> equipos.add(je.getEquipo()));


        // Filtrar partidos relacionados con los equipos del jugador
        List<Partido> partidos = partidoRepository.findAllByEquipo1InOrEquipo2In(equipos, equipos);

        // Filtrar partidos que aún no tienen un ganador (futuros o en curso)
        List<Partido> partidosFiltrados = partidos.stream()
                .filter(partido -> partido.getGanador().isEmpty())
                .collect(Collectors.toList());

        // Ordenar partidos por fecha de inicio
        partidosFiltrados.sort((partido1, partido2) -> {
            Instant fechaInicio1 = partido1.getFechaInicio();
            Instant fechaInicio2 = partido2.getFechaInicio();
            return fechaInicio1.compareTo(fechaInicio2);
        });

        // Mapear partidos a una lista de Map<String, Object>
        List<Map<String, Object>> partidosMap = new ArrayList<>();

        for (Partido partido : partidosFiltrados) {
            Map<String, Object> p = new HashMap<>();
            p.put("idPartido", partido.getClavePartido());
            p.put("arbitro", partido.getArbitro().getUsuario());
            p.put("fechaInicio", partido.getFechaInicio());
            p.put("temporadaId", partido.getTemporada().getClaveTemporada());
            p.put("equipo1", partido.getEquipo1().getNombre());
            p.put("equipo2", partido.getEquipo2().getNombre());
            partidosMap.add(p);
        }

        return partidosMap;
    }

    public List<Map<String, Object>> obtenerPartidosTemporada(Long idTemporada) {
        Optional<Temporada> temporada = temporadaRepository.findById(idTemporada);
        if (!temporada.isPresent()) throw new BadRequestException("La temporada no existe");

        List<Partido> partidos = partidoRepository.findAllByTemporada(temporada.get());

        List<Map<String, Object>> partidosMap = new ArrayList<>();

        for (Partido partido : partidos) {
            Map<String, Object> p = new HashMap<>();
            p.put("idPartido", partido.getClavePartido());
            p.put("arbitro", partido.getArbitro().getUsuario());
            p.put("fechaInicio", partido.getFechaInicio());
            p.put("temporadaId", partido.getTemporada().getClaveTemporada());
            p.put("equipo1", partido.getEquipo1().getNombre());
            p.put("equipo2", partido.getEquipo2().getNombre());
            partidosMap.add(p);
        }

        return partidosMap;

    }

    public ResponseEntity<String> agendarPartido(Partido partido) {
        Optional<Partido> p = partidoRepository.findById(partido.getClavePartido());
        if (!p.isPresent()) throw new BadRequestException("El partido no existe");
        p.get().setFechaInicio(partido.getFechaInicio());
        partidoRepository.save(p.get());
        return ResponseEntity.ok("Partido actualizado exitosamente.");
    }

    public ResponseEntity<String> asignarArbitro(Long idPartido, String idArbitro) {
        Optional<Partido> p = partidoRepository.findById(idPartido);
        if (!p.isPresent()) throw new BadRequestException("El partido no existe");
        Optional<Usuario> arbitro = usuarioRepository.findById(idArbitro);
        if (!arbitro.isPresent()) throw new BadRequestException("El arbitro no existe");
        if (!arbitro.get().getRol().toString().equals("ARBITRO"))
            throw new BadRequestException("El usuario no es un arbitro");

        p.get().setArbitro(arbitro.get());
        partidoRepository.save(p.get());
        return ResponseEntity.ok("Arbitro asignado exitosamente.");
    }


    public ResponseEntity<String> generarPartidosTemporada(Long idTemporada) {
//        -se checa si ya hay 8 equipos en la temporada si no es asi se manda un error
//        tomamos los equipos de la temporada y generamos 4 partidos ya que son 8 equipos
//        se registran los partidos en la bd
//        retornamos los 4 partidos al usuario
        List<Equipo> equipos = equipoTemporadaRepository.findAllEquiposByTemporada(idTemporada);
        if (equipos.size() != 8) throw new BadRequestException("No hay 8 equipos en la temporada");

        List<Partido> partidos = partidoRepository.findAllByTemporada(idTemporada);
        int cantidadPartidosTerminados = 0;
        for(Partido p :partidos){
            if(p.getGanador().length() != 0){
                cantidadPartidosTerminados++;
            }
        }

        if (partidos.size() == 0) {
//            se generan 4 partidos
            for(int i = 0 ; i < equipos.size();i +=2){
                Partido partido = new Partido();
                partido.setTemporada(temporadaRepository.findById(idTemporada).get());
                partido.setEquipo1(equipos.get(i));
                partido.setEquipo2(equipos.get(i+1));
                Fase cuartosDeFinal = Fase.CUARTOS_DE_FINAL;
                partido.setFase(cuartosDeFinal);
                partidoRepository.save(partido);
            }

        } else if (partidos.size() == 4 && cantidadPartidosTerminados == 4) {
        // se generan 2 partidos tomandolos de cuartos de final
            List<Equipo> equiposGanadoresCuartosDeFinal = new ArrayList<>();
            for(Partido p : partidos){
                equiposGanadoresCuartosDeFinal.add(p.obtenerEquipoGanador());
            }
            for(int i = 0; i  < equiposGanadoresCuartosDeFinal.size(); i +=2){
                Partido partido = new Partido();
                partido.setTemporada(temporadaRepository.findById(idTemporada).get());
                partido.setEquipo1(equiposGanadoresCuartosDeFinal.get(i));
                partido.setEquipo2(equiposGanadoresCuartosDeFinal.get(i+1));
                Fase semifinal = Fase.SEMIFINAL;
                partido.setFase(semifinal);
                partidoRepository.save(partido);
            }

        } else if (partidos.size() == 6 && cantidadPartidosTerminados == 6) {
//            se genera un partido
            List<Equipo> equiposGanadoresSemiFinal = new ArrayList<>();
            for(Partido p : partidos){
                if(p.getFase() == Fase.SEMIFINAL){
                    equiposGanadoresSemiFinal.add(p.obtenerEquipoGanador());
                }
            }
            Partido partido = new Partido();
            partido.setTemporada(temporadaRepository.findById(idTemporada).get());
            partido.setEquipo1(equiposGanadoresSemiFinal.get(0));
            partido.setEquipo2(equiposGanadoresSemiFinal.get(1));
            partido.setFase(Fase.FINAL);
            partidoRepository.save(partido);

        } else {
            if(cantidadPartidosTerminados == 7){
                throw new BadRequestException("Ya hay un ganador no se pueden generar mas partidos");
            }
            throw new BadRequestException("Los partidos ya estan generados debe esperar a que haya un resultado para volver a generarlos");
        }

        return ResponseEntity.ok("Se han generado los partidos exitosamente");

    }
}
