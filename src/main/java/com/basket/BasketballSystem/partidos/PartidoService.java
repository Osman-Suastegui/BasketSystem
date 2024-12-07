package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.teams.TeamRepository;
import com.basket.BasketballSystem.equipos_temporadas.EquipoTemporadaRepository;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartido;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartidoRepository;
import com.basket.BasketballSystem.partidos.DTO.PartidoResponse;
import com.basket.BasketballSystem.tournaments.Estado;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.tournaments.TournamentRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartidoService {
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;
    @Autowired
    TournamentRepository temporadaRepository;
    @Autowired
    EquipoTemporadaRepository equipoTemporadaRepository;
    @Autowired
    JugadorPartidoRepository jugadorPartidoRepository;

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
        List<Partido> partidosFiltrados = partidos.stream().filter(partido ->  partido.getFechaInicio() != null ).
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
            p.put("fechaInicio", partido.getFechaInicio());
            Instant fechaInicioPartido = partido.getFechaInicio();
            Instant fechaEndPartido = fechaInicioPartido.plus(durationPartido);
            System.out.println("DURACION DEL PARTIDO INSTANT " + fechaInicioPartido);
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
    public List<Map<String, Object>> obtenerPartidosEquipo(String idEquipo, String estatusPartido) {
        final int duracionPartido = 40; // 40 minutos dura un partido ??
//        ESTATUS PARTIDO enCurso, finalizado, proximo
        List<Map<String, Object>> partidosFinalizadosMap = new ArrayList<>();
        List<Map<String, Object>> partidosEnCursoMap = new ArrayList<>();
        List<Map<String, Object>> partidosProximosMap = new ArrayList<>();
        // Obtén la fecha y hora actual
        Instant horaAcutal = Instant.now();

        Duration durationPartido = Duration.ofMinutes(duracionPartido);
        System.out.println("nombre Partido " + idEquipo + " fin");
        Team team = teamRepository.findByNombre(idEquipo);


        String nombreEquipo = team.getNombre();

        List<Partido> partidos = partidoRepository.findByTeam1NombreOrTeam2Nombre(nombreEquipo);
        List<Partido> partidosFiltrados = partidos.stream().filter(partido -> partido.getFechaInicio() != null ).
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
            p.put("fechaInicio", partido.getFechaInicio());
            Instant fechaInicioPartido = partido.getFechaInicio();
            Instant fechaEndPartido = fechaInicioPartido.plus(durationPartido);

            p.put("temporadaId", partido.getTemporada().getNombreTemporada());
            p.put("equipo1", partido.getEquipo1().getNombre());
            p.put("equipo2", partido.getEquipo2().getNombre());
            p.put("ganador", partido.getGanador());

            if (fechaInicioPartido.isBefore(horaAcutal) && fechaEndPartido.isAfter(horaAcutal)){
                p.put("estatus", "enCurso");
                partidosEnCursoMap.add(p);
            }
            else if (fechaInicioPartido.isAfter(horaAcutal)) {
                p.put("estatus", "proximos");
                partidosProximosMap.add(p);
            }
            else{
                p.put("estatus", "finalizados");
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
        List<Team> teams = new ArrayList<>();
        jugadoresEquipoTmp.forEach(je -> teams.add(je.getEquipo()));


        // Filtrar partidos relacionados con los equipos del jugador
        List<Partido> partidos = partidoRepository.findAllByTeam1InOrTeam2In(teams, teams);

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
            p.put("ganador", partido.getGanador());
            partidosMap.add(p);
        }

        return partidosMap;
    }

    public List<Map<String, Object>> obtenerPartidosTemporada(Long idTemporada) {
        Optional<Tournament> temporada = temporadaRepository.findById(idTemporada);
        if (!temporada.isPresent()) throw new BadRequestException("La temporada no existe");

        List<Partido> partidos = partidoRepository.findAllByTournament(temporada.get());

        List<Map<String, Object>> partidosMap = new ArrayList<>();

        for (Partido partido : partidos) {
            Map<String, Object> p = new HashMap<>();
            p.put("idPartido", partido.getClavePartido());
            if (partido.getArbitro() != null) {
                p.put("arbitro", partido.getArbitro().getUsuario());
            } else {
                p.put("arbitro", "Sin asignar"); // Otra opción, puedes definir un valor por defecto
            }
            p.put("fechaInicio", partido.getFechaInicio());
            p.put("temporadaId", partido.getTemporada().getClaveTemporada());
            p.put("equipo1", partido.getEquipo1().getNombre());
            p.put("equipo2", partido.getEquipo2().getNombre());
            p.put("ganador", partido.getGanador());
            p.put("fase", partido.getFase());
            if (partido.getGanador().isEmpty()) {
                p.put("ganador", "Sin concluir");
            } else {
                p.put("ganador", partido.getGanador());
            }
            partidosMap.add(p);
        }

        return partidosMap;

    }

    public ResponseEntity<Map<String, Object>> agendarPartido(long clavePartido, String fechaInicio) {
        Optional<Partido> partidoOptional = partidoRepository.findById(clavePartido);
        if (partidoOptional.isEmpty()) {
            throw new BadRequestException("El partido no existe");
        }

        Partido partido = partidoOptional.get();
        Tournament temp = partido.getTemporada();

        LocalDate fechaInicioTemp = temp.getStartDate();
        LocalDate fechaFinTemp = temp.getEndDate();

        try {
            // Formatear la fecha y hora en el formato correcto
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date fechaHora = sdf.parse(fechaInicio);

            // Verificar si la fecha está dentro del rango de la temporada
            LocalDate fechaAgendada = fechaHora.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fechaAgendada.isBefore(fechaInicioTemp) || fechaAgendada.isAfter(fechaFinTemp)) {
                throw new BadRequestException("La fecha y hora del partido están fuera del rango de la temporada.");
            }

            // Agregar una hora a la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHora);
            calendar.add(Calendar.HOUR, 1);

            Date fechaConUnaHoraMas = calendar.getTime();
            Instant fechaInicioInstant = fechaConUnaHoraMas.toInstant();

            partido.setFechaInicio(fechaInicioInstant);
            partidoRepository.save(partido);

            Map<String, Object> agendaPartido = new HashMap<>();
            agendaPartido.put("message", "Partido agendado exitosamente.");

            return ResponseEntity.ok(agendaPartido);
        } catch (ParseException e) {
            throw new BadRequestException("Error al analizar la fecha y hora.");
        }
    }

    public ResponseEntity<Map<String, Object>> asignarArbitro(Long idPartido, String idArbitro) {
        Optional<Partido> p = partidoRepository.findById(idPartido);
        if (!p.isPresent()) throw new BadRequestException("El partido no existe");

        if(idArbitro.equals("")){
            p.get().setArbitro(null);
            partidoRepository.save(p.get());

            Map<String, Object> arbitroPartido = new HashMap<>();
            arbitroPartido.put("message", "Árbitro asignado exitosamente.");
            return ResponseEntity.ok(arbitroPartido);
        }

        Optional<Usuario> arbitro = usuarioRepository.findById(idArbitro);

        Instant fechaInicioNuevoPartido = p.get().getFechaInicio();
        Instant fechaFinNuevoPartido = fechaInicioNuevoPartido.plus(Duration.ofHours(1));

        // Obtener todos los partidos del árbitro
        List<Map<String, Object>> partidosArbitro = obtenerPartidosArbitro(idArbitro, "todos");
        for (Map<String, Object> partido : partidosArbitro) {
            Instant fechaInicio = (Instant) partido.get("fechaInicio");
            Instant fechaFin = fechaInicio.plus(Duration.ofHours(1));

            // Verificar si el nuevo partido se superpone con algún partido ya asignado al árbitro
            if ((fechaInicioNuevoPartido.isAfter(fechaInicio) && fechaInicioNuevoPartido.isBefore(fechaFin)) ||
                    (fechaFinNuevoPartido.isAfter(fechaInicio) && fechaFinNuevoPartido.isBefore(fechaFin)) ||
                    fechaInicioNuevoPartido.equals(fechaInicio) || fechaFinNuevoPartido.equals(fechaFin)) {
                throw new BadRequestException("El árbitro ya tiene un partido asignado en ese horario");
            }
        }

        p.get().setArbitro(arbitro.get());
        partidoRepository.save(p.get());

        Map<String, Object> arbitroPartido = new HashMap<>();
        arbitroPartido.put("message", "Árbitro asignado exitosamente.");

        return ResponseEntity.ok(arbitroPartido);
    }


    public ResponseEntity<Map<String,Object>> crearPartidosTemporadaRegular(Long idTemporada, int cantidadEnfrentamientosRegular){
        Tournament t = temporadaRepository.findById(idTemporada).orElse(null);
        Estado estado = Estado.ACTIVA;
        t.setEstado(estado);
        if(t == null) throw new BadRequestException("La temporada no existe");
        if(cantidadEnfrentamientosRegular != 1 && cantidadEnfrentamientosRegular != 2) throw new BadRequestException("La cantidad de enfrentamientos debe ser 1 o 2");

        // equipos que participan en la temporada
        List<Team> teams = equipoTemporadaRepository.findAllTeamsByTournament(idTemporada);
        List<Partido> partidos = new ArrayList<>();
        for(int i = 0; i < teams.size()-1; i++){

            for(int j = i+1; j < teams.size(); j++){
                Partido partido = new Partido();
                partido.setEquipo1(teams.get(i));
                partido.setEquipo2(teams.get(j));
                partido.setTemporada(t);
                partido.setFase(Fase.REGULAR);
                partidos.add(partido);
                if(cantidadEnfrentamientosRegular == 2){
                    partido = new Partido();
                    partido.setEquipo1(teams.get(i));
                    partido.setEquipo2(teams.get(j));
                    partido.setTemporada(t);
                    partido.setFase(Fase.REGULAR);
                    partidos.add(partido);
                }


            }
        }

        for(Partido p : partidos){
            partidoRepository.save(p);
        }
        return ResponseEntity.ok().build();

    }

//    Metodo en Mantenimiento
    public ResponseEntity<Map<String, Object>>  generarPartidosTemporada(Long idTemporada) {
////        -se checa si ya hay 8 equipos en la temporada si no es asi se manda un error
////        tomamos los equipos de la temporada y generamos 4 partidos ya que son 8 equipos
////        se registran los partidos en la bd
////        retornamos los 4 partidos al usuario
//        List<Equipo> equipos = equipoTemporadaRepository.findAllEquiposByTemporada(idTemporada);
//        if (equipos.size() != 8) throw new BadRequestException("No hay 8 equipos en la temporada");
//
//        List<Partido> partidos = partidoRepository.findAllByTemporada(idTemporada);
//        int cantidadPartidosTerminados = 0;
//        for(Partido p :partidos){
//            if(p.getGanador().length() != 0){
//                cantidadPartidosTerminados++;
//            }
//        }
//
//        if (partidos.size() == 0) {
////            se generan 4 partidos
//            for(int i = 0 ; i < equipos.size();i +=2){
//                Partido partido = new Partido();
//                partido.setTemporada(temporadaRepository.findById(idTemporada).get());
//                partido.setEquipo1(equipos.get(i));
//                partido.setEquipo2(equipos.get(i+1));
//                Fase cuartosDeFinal = Fase.CUARTOS_DE_FINAL;
//                partido.setFase(cuartosDeFinal);
//                partidoRepository.save(partido);
//                //cambia el estado de la temporada a activa
//                temporadaRepository.updateTemporadaEstado(idTemporada,"ACTIVA");
//
//            }
//
//        } else if (partidos.size() == 4 && cantidadPartidosTerminados == 4) {
//        // se generan 2 partidos tomandolos de cuartos de final
//            List<Equipo> equiposGanadoresCuartosDeFinal = new ArrayList<>();
//            for(Partido p : partidos){
//                equiposGanadoresCuartosDeFinal.add(p.obtenerEquipoGanador());
//            }
//            for(int i = 0; i  < equiposGanadoresCuartosDeFinal.size(); i +=2){
//                Partido partido = new Partido();
//                partido.setTemporada(temporadaRepository.findById(idTemporada).get());
//                partido.setEquipo1(equiposGanadoresCuartosDeFinal.get(i));
//                partido.setEquipo2(equiposGanadoresCuartosDeFinal.get(i+1));
//                Fase semifinal = Fase.SEMIFINAL;
//                partido.setFase(semifinal);
//                partidoRepository.save(partido);
//            }
//
//        } else if (partidos.size() == 6 && cantidadPartidosTerminados == 6) {
////            se genera un partido
//            List<Equipo> equiposGanadoresSemiFinal = new ArrayList<>();
//            for(Partido p : partidos){
//                if(p.getFase() == Fase.SEMIFINAL){
//                    equiposGanadoresSemiFinal.add(p.obtenerEquipoGanador());
//                }
//            }
//            Partido partido = new Partido();
//            partido.setTemporada(temporadaRepository.findById(idTemporada).get());
//            partido.setEquipo1(equiposGanadoresSemiFinal.get(0));
//            partido.setEquipo2(equiposGanadoresSemiFinal.get(1));
//            partido.setFase(Fase.FINAL);
//            partidoRepository.save(partido);
//
//        } else {
//            if(cantidadPartidosTerminados == 7){
//                throw new BadRequestException("Ya hay un ganador no se pueden generar mas partidos");
//            }
//            throw new BadRequestException("Los partidos ya estan generados debe esperar a que haya un resultado para volver a generarlos");
//        }
//        Map<String, Object> partidoMes = new HashMap<>();
//        partidoMes.put("message", "Se han generado los partidos exitosamente.");
//
        return ResponseEntity.ok().build();
    }

    public Map<String,Integer> rankingTemporadaRegular(Long idTemporada){
        List<Team> teams = equipoTemporadaRepository.findAllTeamsByTournament(idTemporada);
        Map<String,Integer> equiposPuntos = new HashMap<>();
        for(Team e : teams){
            equiposPuntos.put(e.getNombre(),0);
        }
        List<Partido> partidos = partidoRepository.findAllByTournament(idTemporada);
        for(Partido p : partidos){
            if(p.getGanador().length() != 0 ){
                if(p.getGanador().equals("EMPATE")){
                    equiposPuntos.put(p.getEquipo1().getNombre(),equiposPuntos.get(p.getEquipo1().getNombre())+1);
                    equiposPuntos.put(p.getEquipo2().getNombre(),equiposPuntos.get(p.getEquipo2().getNombre())+1);
                }
                else if(p.getGanador().equals(p.getEquipo1().getNombre())){
                    equiposPuntos.put(p.getEquipo1().getNombre(),equiposPuntos.get(p.getEquipo1().getNombre())+3);
                }else{
                    equiposPuntos.put(p.getEquipo2().getNombre(),equiposPuntos.get(p.getEquipo2().getNombre())+3);
                }
            }
        }
//        return equipos puntos
        return equiposPuntos;

    }



    public Map<String, Map<String, Integer>> obtenerRankingTemporadaRegular(Long idTemporada) {
        List<Team> teams = equipoTemporadaRepository.findAllTeamsByTournament(idTemporada);
        List<Partido> partidos = partidoRepository.findAllByTournament(idTemporada);

        Map<String, Map<String, Integer>> equiposInfo = new HashMap<>();

        // Inicializar la información de cada equipo en el mapa
        for (Team e : teams) {
            Map<String, Integer> equipoInfo = new HashMap<>();
            equipoInfo.put("puntosTemporada", 0);
            equipoInfo.put("jugados", 0);
            equipoInfo.put("ganados", 0);
            equipoInfo.put("perdidos", 0);
            equipoInfo.put("puntosJugador", 0);
            equiposInfo.put(e.getNombre(), equipoInfo);
        }

        for (Partido p : partidos) {
            if (!p.getGanador().isEmpty()) {
                // Actualizar partidos jugados para ambos equipos
                actualizarContadores(equiposInfo, p.getEquipo1().getNombre(), "jugados");
                actualizarContadores(equiposInfo, p.getEquipo2().getNombre(), "jugados");

                if (p.getGanador().equals("EMPATE")) {
                    // Actualizar partidos empatados para ambos equipos
                    actualizarContadores(equiposInfo, p.getEquipo1().getNombre(), "puntosTemporada");
                    actualizarContadores(equiposInfo, p.getEquipo2().getNombre(), "puntosTemporada");
                } else if (p.getGanador().equals(p.getEquipo1().getNombre())) {
                    // Actualizar partidos ganados para el equipo 1
                    actualizarContadores(equiposInfo, p.getEquipo1().getNombre(), "puntosTemporada", 3);
                    actualizarContadores(equiposInfo, p.getEquipo1().getNombre(), "ganados");

                    // Actualizar partidos perdidos para el equipo 2
                    actualizarContadores(equiposInfo, p.getEquipo2().getNombre(), "perdidos");
                } else {
                    // Actualizar partidos ganados para el equipo 2
                    actualizarContadores(equiposInfo, p.getEquipo2().getNombre(), "puntosTemporada", 3);
                    actualizarContadores(equiposInfo, p.getEquipo2().getNombre(), "ganados");

                    // Actualizar partidos perdidos para el equipo 1
                    actualizarContadores(equiposInfo, p.getEquipo1().getNombre(), "perdidos");
                }
            }
        }

        // Calcular puntos adicionales de los jugadores
        calcularPuntosJugador(idTemporada, equiposInfo);

        // Retornar información de equipos
        return equiposInfo;
    }

    private void actualizarContadores(Map<String, Map<String, Integer>> equiposInfo, String nombreEquipo, String key) {
        equiposInfo.computeIfPresent(nombreEquipo, (k, equipoInfo) -> {
            equipoInfo.put(key, equipoInfo.get(key) + 1);
            return equipoInfo;
        });
    }

    private void actualizarContadores(Map<String, Map<String, Integer>> equiposInfo, String nombreEquipo, String key, int valor) {
        equiposInfo.computeIfPresent(nombreEquipo, (k, equipoInfo) -> {
            equipoInfo.put(key, equipoInfo.get(key) + valor);
            return equipoInfo;
        });
    }

    private void calcularPuntosJugador(Long idTemporada, Map<String, Map<String, Integer>> equiposInfo) {
        List<Partido> partidos = partidoRepository.findAllByTournament(idTemporada);

        for (Partido p : partidos) {
            List<JugadorPartido> jugadoresPartido = jugadorPartidoRepository.findAllByPartido(p.getClavePartido());

            for (JugadorPartido jp : jugadoresPartido) {
                // Actualizar puntos de cada jugador al equipo correspondiente
             //   actualizarContadores(equiposInfo, jp.getEquipo(), "puntosJugador", jp.getAnotaciones());
                actualizarContadores(equiposInfo, jp.getEquipo(), "puntosJugador", jp.getTirosDe2Puntos() * 2);
                actualizarContadores(equiposInfo, jp.getEquipo(), "puntosJugador", jp.getTirosDe3Puntos() * 3);
                actualizarContadores(equiposInfo, jp.getEquipo(), "puntosJugador", jp.getTirosLibres());
            }
        }
    }



    public ResponseEntity<PartidoResponse> obtenerPartido(Long idPartido) {
         Optional<Partido> partido = partidoRepository.findById(idPartido);
            if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

            PartidoResponse partidoResponse = new PartidoResponse();
            partidoResponse.setClavePartido(partido.get().getClavePartido());
            partidoResponse.setFase(partido.get().getFase().toString());
            partidoResponse.setFechaInicio(partido.get().getFechaInicio().toString());
            partidoResponse.setEquipo1(partido.get().getEquipo1().getNombre());
            partidoResponse.setEquipo2(partido.get().getEquipo2().getNombre());
            partidoResponse.setArbitro(partido.get().getArbitro().getUsuario());
            partidoResponse.setResultado(partido.get().getGanador());
            partidoResponse.setClaveTemporada(partido.get().getTemporada().getClaveTemporada());
            return ResponseEntity.ok(partidoResponse);
    }

    public ResponseEntity<Map<String, Object>> obtenerEquipo1Equipo2(Long idPartido) {
        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

        Map<String, Object> equipo1Equipo2 = new HashMap<>();
        equipo1Equipo2.put("equipo1", partido.get().getEquipo1().getNombre());
        equipo1Equipo2.put("equipo2", partido.get().getEquipo2().getNombre());

        return ResponseEntity.ok(equipo1Equipo2);
    }

//    METODO PARA TEST NADA MAS
    public void setGanadorAleatorio(Long idTemporada){

        List<Partido> partidos = partidoRepository.findAllByTournament(idTemporada);
        for(Partido p : partidos){

                Random r = new Random();
                int random = r.nextInt(3);
                if(random == 0){
                    p.setGanador(p.getEquipo1().getNombre());
                }else if(random == 1){
                    p.setGanador(p.getEquipo2().getNombre());
                }else{
                    p.setGanador("EMPATE");
                }
                partidoRepository.save(p);
        }
    }


    // verificar si ya se jugaron todos los partidos de la temporada en la fase regular
    public boolean verificarSiSeJugaronTodosLosPartidosRegular(Long idTemporada){
        List<Partido> partidos = partidoRepository.findAllByTournament(idTemporada);
        int cantidadPartidosTerminados = 0;
        for(Partido p :partidos){
            if(p.getGanador().length() != 0){
                cantidadPartidosTerminados++;
            }
        }
        if(cantidadPartidosTerminados == partidos.size()){
            return true;
        }
        return false;
    }
    public List<String> SeleccionarEquiposQuePasanAPlayoffs(Long idTemporada){
        Tournament t = temporadaRepository.findById(idTemporada).orElse(null);
        if(t == null) throw new BadRequestException("La temporada no existe");


        if(!verificarSiSeJugaronTodosLosPartidosRegular(idTemporada)){
            throw new BadRequestException("No se han jugado todos los partidos de la temporada en la fase regular");
        }
        Map<String,Integer> rankingEquipos =  rankingTemporadaRegular(idTemporada);
//        crea un arraylist de dos valores que tenga un string y integer y ordenalos por integer de mayor a menor

        List<Map.Entry<String, Integer>> list = new ArrayList<>(rankingEquipos.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        List<String> equiposQuePasanAPlayoffs = new ArrayList<>();
        int cantidadEquiposQuePasanAPlayoffs =  t.getCantidadPlayoffs();

        for(int i =0 ; i < cantidadEquiposQuePasanAPlayoffs;i++){
            equiposQuePasanAPlayoffs.add(list.get(i).getKey());
        }

        return equiposQuePasanAPlayoffs;

    }
    public void crearPartidosEliminatorias(Long idTemporada) {
        Tournament tempo = temporadaRepository.findById(idTemporada).orElse(null);
        if (tempo == null) throw new BadRequestException("La temporada no existe");
        int cantidadEquiposQuePasanAPlayoffs = tempo.getCantidadPlayoffs();
//        tengo que checar si existen partidos con la fase de eliminatorias si es asi creo los partidos de eliminatorias
        List<Partido> partidosEliminatorias = partidoRepository.findAllByTournamentAndFase(idTemporada, Fase.Eliminatorias);
        int cantidadJuegosTerminadodosEliminatorias = 0;


//        Generamos partidos primera vez cuando los partidos de eliminatorias estan vacios y ya se jugaron todos los de la fase regular
        if (partidosEliminatorias.size() == 0) {
            List<String> equiposQuePasanPlayoff = SeleccionarEquiposQuePasanAPlayoffs(idTemporada);
//            formamos los primeros partidos de eliminatorias
            for (int i = 0; i < equiposQuePasanPlayoff.size() / 2; i++) {
                Partido partido = new Partido();
                partido.setTemporada(tempo);
                partido.setEquipo1(teamRepository.findByNombre(equiposQuePasanPlayoff.get(i)));
                partido.setEquipo2(teamRepository.findByNombre(equiposQuePasanPlayoff.get(equiposQuePasanPlayoff.size() - 1 - i)));
                partido.setFase(Fase.Eliminatorias);
                partidoRepository.save(partido);
            }
        } else {
//        Lo que se hace es que queremos saber la cantidad De partidos que se deben jugar en las eliminatorias en cierto momento
//        dependiendo de ese momento decidimos si podemos generar nuevos partidos para la siguiente parte de las eliminatorias
//        por ejemplo si cantidadEquiposQuePasanAPlayoffs = 8  En total se deben jugar 7 partidos en las eliminatorias
//        si cantidadEquiposQuePasanAPlayoffs = 16 En total se deben jugar 15 partidos en las eliminatorias
//        por lo debemos saber en que etapa estamos y cuando debemos generar partidos si cantidadEquiposQuePasanAPlayoffs = 8
//        entonces primer etapa es 4 partidos y la siguiente etapa es 2 partidos y la ultima etapa es 1 partido
//        Entonces ¿cuando generamos partidos? primero se genera cuando no hay equipos registrados en la fase de eliminatorias y
//        ya se jugaron todos los de la fase regular
//        despues se genera cuando ya se jugaron todos los partidos de la primera etapa y asi sucesivamente
//        4 = 4 primera etapa
//        4 + 2 = 6 segunda etapa
//        4 + 2 + 1 = 7 tercera etapa
//        por lo que tenmos que dividir entre 2 y acumular y verificar si la cantidad de partidos que se jugaron es igual a la cantidad de partidos que se deben jugar
//        ¿como seleccionamos a los siguientes equipos que pasan a la siguiente etapa? for fecha ¿cuantos pasan? podemos ver en el ejemplo anterior
//        ¿cuando debemos dejar de generar partidos? cantidadEquiposQuePasanAPlayoffs - 1 = cantidad de partidos finalizados en las Eliminatorias





            for (Partido p : partidosEliminatorias) {
                if (!p.getGanador().isEmpty()) {
                    cantidadJuegosTerminadodosEliminatorias++;
                }
            }
            if (cantidadJuegosTerminadodosEliminatorias == cantidadEquiposQuePasanAPlayoffs - 1) {
                throw new BadRequestException("Ya se han jugado todos los partidos de las eliminatorias");
            }
            boolean sePuedenGenerarPartidos = false;
            int cantidadJuegosQueSeDebenJugarEliminatorias = 0;
            while (cantidadEquiposQuePasanAPlayoffs > 1) {
                cantidadEquiposQuePasanAPlayoffs /= 2;
                cantidadJuegosQueSeDebenJugarEliminatorias += cantidadEquiposQuePasanAPlayoffs;
                if (partidosEliminatorias.size() == cantidadJuegosTerminadodosEliminatorias && cantidadJuegosQueSeDebenJugarEliminatorias == partidosEliminatorias.size()) {
                    partidosEliminatorias.sort((partido1, partido2) -> {
                        Instant fechaInicio1 = partido1.getFechaInicio();
                        Instant fechaInicio2 = partido2.getFechaInicio();
                        return fechaInicio2.compareTo(fechaInicio1);
                    });
                    sePuedenGenerarPartidos = true;
//                    generamos nuevos partidos para eliminatorias
                    for (int i = 0; i < cantidadEquiposQuePasanAPlayoffs; i += 2) {
                        Partido partido = new Partido();
                        partido.setTemporada(tempo);
                        String NombreequipoGanadorPartido = partidosEliminatorias.get(i).obtenerEquipoGanador();
                        Team teamGanadorPartido1 = teamRepository.findByNombre(NombreequipoGanadorPartido);
                        partido.setEquipo1(teamGanadorPartido1);
                        NombreequipoGanadorPartido = partidosEliminatorias.get(i + 1).obtenerEquipoGanador();
                        Team teamGanadorPartido2 = teamRepository.findByNombre(NombreequipoGanadorPartido);
                        partido.setEquipo2(teamGanadorPartido2);
                        partido.setFase(Fase.Eliminatorias);
                        partidoRepository.save(partido);
                    }
                    break;

                }
            }
            if (!sePuedenGenerarPartidos) {
                throw new BadRequestException("Los partidos de eliminatorias aun no acaban");
            }
        }


    }


    public ResponseEntity<Map<String, Object>> obtenerFechaInicio(Long idPartido) {
        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

        Map<String, Object> fechaInicio = new HashMap<>();
        fechaInicio.put("fechaInicio", partido.get().getFechaInicio());

        return ResponseEntity.ok(fechaInicio);
    }

    public ResponseEntity<Map<String, Object>> decidirGanadorPartido(Long idPartido) {
        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

        String equipo1 = partido.get().getEquipo1().getNombre();
        String equipo2 = partido.get().getEquipo2().getNombre();
        int anotacionesEquipo1 = jugadorPartidoRepository.sumarPuntosPorEquipoYPartido(equipo1,idPartido);
        int anotacionesEquipo2 = jugadorPartidoRepository.sumarPuntosPorEquipoYPartido(equipo2,idPartido);

        if(anotacionesEquipo1 > anotacionesEquipo2){
            partido.get().setGanador(equipo1);
        }
        if(anotacionesEquipo2 > anotacionesEquipo1 ){
            partido.get().setGanador(equipo2);
        }
        partidoRepository.save(partido.get());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Map<String, Object>> obtenerGanador(Long idPartido) {

        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

        Map<String, Object> ganador = new HashMap<>();
        if(partido.get().getGanador().isEmpty() || partido.get() == null){
            ganador.put("ganador","");
        }else{
            ganador.put("ganador", partido.get().getGanador());
        }

        return ResponseEntity.ok(ganador);
    }

    public ResponseEntity<Map<String, Object>> finalizarPartido(Long idPartido) {
        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");
        if(partido.get().getFechaInicio().isAfter(Instant.now())){
            throw new BadRequestException("El partido aun no ha iniciado");
        }
        decidirGanadorPartido(idPartido);
//        si es el ultimo partido de la temporada se debe cambiar el estado de la temporada a finalizada
        Tournament temp = partido.get().getTemporada();
        int cantidadPlayOffs = temp.getCantidadPlayoffs();
        Long idTemporada = temp.getClaveTemporada();

        int cantidadDeJuegosQueSeDebenJugar = 0;
        while(cantidadPlayOffs > 1){
            cantidadPlayOffs /= 2;
            cantidadDeJuegosQueSeDebenJugar += cantidadPlayOffs;
        }
//        obtenemos la cantidad de juegos que tienen la fase eliminatorias y que ya tiene un ganador
        int cantidadDeJuegosTerminados = 0;
        List<Partido> partidosEliminatorias = partidoRepository.findAllByTournamentAndFase(idTemporada, Fase.Eliminatorias);
        for(Partido p : partidosEliminatorias){
            if(!p.getGanador().isEmpty()){
                cantidadDeJuegosTerminados++;
            }
        }
        if(cantidadDeJuegosTerminados == cantidadDeJuegosQueSeDebenJugar){
            temporadaRepository.updateTemporadaEstado(idTemporada,"FINALIZADA");
        }
        Map<String, Object> finalizarPartido = new HashMap<>();
        finalizarPartido.put("message", "Partido finalizado exitosamente.");
        return ResponseEntity.ok(finalizarPartido);
    }

    public ResponseEntity<Map<String, Object>> obtenerUsuarioArbitroAsignado(Long idPartido) {

        String nombre = partidoRepository.findArbitroByClavePartido(idPartido);
        Map<String, Object> nombreArbitro = new HashMap<>();
        nombreArbitro.put("usuarioArbitro", nombre);
        return ResponseEntity.ok(nombreArbitro);
    }

    public ResponseEntity<Map<String, Object>> arbitroIniciaPartidoFecha(Long idPartido, String fecha) {
        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

        try {
            // Formatear la fecha y hora en el formato correcto
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fechaHora = sdf.parse(fecha);

            // Verificar si la fecha está dentro del rango de la temporada
            LocalDate fechaAgendada = fechaHora.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // Agregar una hora a la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHora);
            calendar.add(Calendar.HOUR, 1);

            Date fechaConUnaHoraMas = calendar.getTime();
            Instant fechaInicioInstant = fechaConUnaHoraMas.toInstant();

            partido.get().setArbitroIniciaPartido(fechaInicioInstant);
            partidoRepository.save(partido.get());

            Map<String, Object> arbitroPartido = new HashMap<>();
            arbitroPartido.put("message", "Partido iniciado exitosamente.");

            return ResponseEntity.ok(arbitroPartido);
        } catch (ParseException e) {
            throw new BadRequestException("Error al analizar la fecha y hora.");
        }
    }

    public ResponseEntity<Map<String, Object>> obtenerArbitroIniciaPartidoFecha(Long idPartido) {
        Optional<Partido> partido = partidoRepository.findById(idPartido);
        if (!partido.isPresent()) throw new BadRequestException("El partido no existe");

        Map<String, Object> arbitroIniciaPartido = new HashMap<>();
        arbitroIniciaPartido.put("fechaInicio", partido.get().getArbitroIniciaPartido());

        return ResponseEntity.ok(arbitroIniciaPartido);
    }
}
