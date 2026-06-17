package com.alberto.auth.services;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.alberto.auth.entities.Usuario;
import com.alberto.auth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Buscando usuario {}", username);

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRoles().stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                        .collect(Collectors.toSet())
        );
    }
}

