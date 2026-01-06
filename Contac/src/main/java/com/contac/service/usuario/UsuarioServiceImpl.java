package com.contac.service;

import com.contac.entity.Usuario;
import com.contac.repository.UsuarioRepository;
import com.contac.service.usuario.IUsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(String username, String password) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .rol("ADMIN")
                .build();

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuario(String username) {
        return usuarioRepository.existsByUsername(username);
    }
}