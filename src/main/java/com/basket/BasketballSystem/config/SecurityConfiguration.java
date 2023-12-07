package com.basket.BasketballSystem.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private static final String[] WHITE_LIST_URL = {"/auth/**","/Partido/**","/usuarios/**","/JugadorPartido/**","/Temporadas/**",
            "/Equipo/**","/estadisticas/**","/EquipoTemporada/**","/jugadores-equipo/**","/Ligas/**"
    };
    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                req.requestMatchers(HttpMethod.GET,WHITE_LIST_URL).permitAll().requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                        .requestMatchers("/ws-basket/**").permitAll()
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

