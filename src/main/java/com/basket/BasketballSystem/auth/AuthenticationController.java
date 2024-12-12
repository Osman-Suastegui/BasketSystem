package com.basket.BasketballSystem.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        System.out.print("auth");
        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
    }
}
