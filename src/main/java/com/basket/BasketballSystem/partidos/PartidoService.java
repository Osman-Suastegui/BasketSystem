package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.equipos.EquipoRepository;
import com.basket.BasketballSystem.equipos_temporadas.EquipoTemporadaRepository;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipoRepository;
import com.basket.BasketballSystem.partidos.DTO.PartidoResponse;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.temporadas.TemporadaRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartidoService {
    @Autowired
    PartidoRepository partidoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EquipoRepository equipoRepository;

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
        Equipo equipo = equipoRepository.findByNombre(idEquipo);


        String nombreEquipo = equipo.getNombre();

        List<Partido> partidos = partidoRepository.findByEquipo1NombreOrEquipo2Nombre(nombreEquipo);
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
            p.put("ganador", partido.getGanador());
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

        try {
            System.out.println(fechaInicio);
            // Formatear la fecha y hora en el formato correcto
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date fechaHora = sdf.parse(fechaInicio);

            // Agregar una hora a la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHora);
            calendar.add(Calendar.HOUR, 1);

            Date fechaConUnaHoraMas = calendar.getTime();
            Instant fechaInicioInstant = fechaConUnaHoraMas.toInstant();

            partido.setFechaInicio(fechaInicioInstant);
            System.out.println("fecha inicio instant: " + fechaInicioInstant);
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
        Optional<Usuario> arbitro = usuarioRepository.findById(idArbitro);
        if (!arbitro.isPresent()) throw new BadRequestException("El arbitro no existe");

        p.get().setArbitro(arbitro.get());
        partidoRepository.save(p.get());

        Map<String, Object> arbitroPartido = new HashMap<>();
        arbitroPartido.put("message", "Arbitro asignado exitosamente.");

        return ResponseEntity.ok(arbitroPartido);
    }


    public ResponseEntity<Map<String,Object>> generarPartidosTemporadaRegular(Long idTemporada, int cantidadEnfrentamientosRegular){
        Temporada t = temporadaRepository.findById(idTemporada).orElse(null);
        if(t == null) throw new BadRequestException("La temporada no existe");
        if(cantidadEnfrentamientosRegular != 1 && cantidadEnfrentamientosRegular != 2) throw new BadRequestException("La cantidad de enfrentamientos debe ser 1 o 2");

        // equipos que participan en la temporada
        List<Equipo> equipos = equipoTemporadaRepository.findAllEquiposByTemporada(idTemporada);
        List<Partido> partidos = new ArrayList<>();
        for(int i = 0 ; i < equipos.size()-1;i++){

            for(int j = i+1; j < equipos.size();j++){
                Partido partido = new Partido();
                partido.setEquipo1(equipos.get(i));
                partido.setEquipo2(equipos.get(j));
                partido.setTemporada(t);
                partido.setFase(Fase.REGULAR);
                partidos.add(partido);
                if(cantidadEnfrentamientosRegular == 2){
                    partido = new Partido();
                    partido.setEquipo1(equipos.get(i));
                    partido.setEquipo2(equipos.get(j));
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
        List<Equipo> equipos = equipoTemporadaRepository.findAllEquiposByTemporada(idTemporada);
        Map<String,Integer> equiposPuntos = new HashMap<>();
        for(Equipo e : equipos){
            equiposPuntos.put(e.getNombre(),0);
        }
        List<Partido> partidos = partidoRepository.findAllByTemporada(idTemporada);
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

        List<Partido> partidos = partidoRepository.findAllByTemporada(idTemporada);
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
        List<Partido> partidos = partidoRepository.findAllByTemporada(idTemporada);
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
        Temporada t = temporadaRepository.findById(idTemporada).orElse(null);
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
        Temporada tempo = temporadaRepository.findById(idTemporada).orElse(null);
        if (tempo == null) throw new BadRequestException("La temporada no existe");
        int cantidadEquiposQuePasanAPlayoffs = tempo.getCantidadPlayoffs();
//        tengo que checar si existen partidos con la fase de eliminatorias si es asi creo los partidos de eliminatorias
        List<Partido> partidosEliminatorias = partidoRepository.findAllByTemporadaAndFase(idTemporada, Fase.Eliminatorias);
        int cantidadJuegosTerminadodosEliminatorias = 0;


//        Generamos partidos primera vez cuando los partidos de eliminatorias estan vacios y ya se jugaron todos los de la fase regular
        if (partidosEliminatorias.size() == 0) {
            List<String> equiposQuePasanPlayoff = SeleccionarEquiposQuePasanAPlayoffs(idTemporada);
//            formamos los primeros partidos de eliminatorias
            for (int i = 0; i < equiposQuePasanPlayoff.size() / 2; i++) {
                Partido partido = new Partido();
                partido.setTemporada(tempo);
                partido.setEquipo1(equipoRepository.findByNombre(equiposQuePasanPlayoff.get(i)));
                partido.setEquipo2(equipoRepository.findByNombre(equiposQuePasanPlayoff.get(equiposQuePasanPlayoff.size() - 1 - i)));
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
                        Equipo equipoGanadorPartido1 = equipoRepository.findByNombre(NombreequipoGanadorPartido);
                        partido.setEquipo1(equipoGanadorPartido1);
                        NombreequipoGanadorPartido = partidosEliminatorias.get(i + 1).obtenerEquipoGanador();
                        Equipo equipoGanadorPartido2 = equipoRepository.findByNombre(NombreequipoGanadorPartido);
                        partido.setEquipo2(equipoGanadorPartido2);
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


}
