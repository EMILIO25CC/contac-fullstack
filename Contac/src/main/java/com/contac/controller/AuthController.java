package com.contac.controller;


import com.contac.config.JwtUtil;
import com.contac.dto.auth.LoginRequestDTO;
import com.contac.dto.auth.RegistroRequestDTO;
import com.contac.entity.Usuario;
import com.contac.service.usuario.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IUsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(IUsuarioService usuarioService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroRequestDTO dto) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(dto.getUsername(), dto.getPassword());

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("username", usuario.getUsername());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );

            String token = jwtUtil.generateToken(dto.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", dto.getUsername());
            response.put("tipo", "Bearer");

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Credenciales inválidas"));
        }
    }

    @GetMapping("/verificar/{username}")
    public ResponseEntity<?> verificarUsuario(@PathVariable String username) {
        boolean existe = usuarioService.existeUsuario(username);
        return ResponseEntity.ok(Map.of("existe", existe));
    }
}