package com.basket.BasketballSystem.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.basket.BasketballSystem.usuarios.Rol.ADMIN_LIGA;
import static com.basket.BasketballSystem.usuarios.Rol.ADMIN_EQUIPO;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity

public class SecurityConfiguration {

    private final  JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsFilter corsFilter;
    private static final String[] WHITE_LIST_URL = {"/auth/**","/Partido/**","/usuarios/**","/JugadorPartido/**","/ws-basket/**",
            "/auth/register",
            "/auth/authenticate",
            "/Partido/obtenerPartidosArbitro",
            "/Partido/obtenerPartidosEquipo",
            "/Partido/obtenerPartido",
            "/Partido/rankingTemporadaRegular",
            "/Partido/obtenerFechaInicio",
            "/Partido/obtenerGanador",
            "/Partido/obtenerUsuarioArbitroAsignado",
            "/Partido/obtenerArbitroIniciaPartidoFecha",
            "swagger-ui/**",
            "/v3/**",
            "/usuarios/obtenerUsuario",
            "/usuarios/obtenerJugador",
            "/usuarios/obtenerTipoUser",
            "/usuarios/obtenerUsuarioPorUser",

            "/JugadorPartido/obtenerJugadoresDePartidoyEquipo",
            "/JugadorPartido/obtenerPuntosEquipo",
            "/Temporadas/buscarTemporadasPorNombre",

            "/Ligas/obtenerTemporadas",
            "/Ligas/obtenerAdministradores",
            "/Ligas/buscarLigaPorNombre",

            "/estadisticas/jugador-temporada",
            "/estadisticas/jugador-general",
            "/estadisticas/equipo-temporada-estadisticas",
            "/Equipo/**",
            "/ws-basket/**",
            "/api/test/**",
            "/tournaments/**",
            "/teams/**",
            "/team-tournament-links/**",
            "/players/**"


    };
    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}

