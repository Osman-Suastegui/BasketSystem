package com.basket.BasketballSystem.auth;

import com.basket.BasketballSystem.config.JwtService;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("El correo electrónico ya está registrado.");
        }
        if (registerRequest.getFechaNacimiento() == null)
            throw new BadRequestException("La fecha de nacimiento no puede ser nula.");

        Usuario user = Usuario.builder()
                .usuario(registerRequest.getUsuario())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .rol(registerRequest.getRol())
                .fechaNacimiento(registerRequest.getFechaNacimiento())
                .genero(registerRequest.getGenero())
                .nombre(registerRequest.getNombre())
                .apellido(registerRequest.getApellido())
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getUsuario(),
                        authenticateRequest.getPassword()
                )
        );
        Usuario user = userRepository.findByUsuario(authenticateRequest.getUsuario()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
