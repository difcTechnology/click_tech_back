package com.clicktech.backend.service;

import com.clicktech.backend.dto.LoginRequest;
import com.clicktech.backend.dto.LoginResponse;
import com.clicktech.backend.dto.RegisterRequest;
import com.clicktech.backend.entity.Usuario;
import com.clicktech.backend.repository.UsuarioRepository;
import com.clicktech.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (usuario.getActivo() == null || usuario.getActivo() != 1) {
            throw new RuntimeException("La cuenta está desactivada");
        }
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());
        return new LoginResponse(
                token,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getIdUsuario()
        );
    }

    public Usuario register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(1);
        return usuarioRepository.save(usuario);
    }
}


