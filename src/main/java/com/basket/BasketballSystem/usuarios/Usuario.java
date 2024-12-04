package com.basket.BasketballSystem.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// CHANGE NAME TO USER
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate numeric ID
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    private String usuario;

    @Column(unique = true)
    @NotEmpty(message = "el email no puede estar vacio") @NotNull(message = "el email no puede ser nulo") @NotBlank(message = "el email no puede estar en blanco")
    private String email;
    @NotEmpty(message = "la contraseña no puede estar vacio") @NotNull(message = "la contraseña no puede ser nulo") @NotBlank(message = "la contraseña no puede estar en blanco")

    private String password;
    @NotEmpty(message = "el nombre no puede estar vacio") @NotNull(message = "el nombre no puede ser nulo") @NotBlank(message = "el nombre no puede estar en blanco")

    private String nombre;

    @NotEmpty(message = "el apellido no puede estar vacio") @NotNull(message = "el apellido no puede ser nulo") @NotBlank(message = "el apellido no puedo estar en blanco")
    String apellido;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}