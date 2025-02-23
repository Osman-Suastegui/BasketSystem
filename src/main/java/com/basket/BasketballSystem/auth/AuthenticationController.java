package com.basket.BasketballSystem.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Validated @RequestBody RegisterRequest registerRequest) throws Exception{


        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @CrossOrigin(origins = "http://localhost:4200") // Allow Angular app's URL
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticateRequest){
        log.info("Received authentication request for user: {}", authenticateRequest.toString());
        AuthenticationResponse response = authenticationService.authenticate(authenticateRequest);
        log.info("Authentication successful for user: {}", authenticateRequest.toString());
        return ResponseEntity.ok(response);
    }
}
