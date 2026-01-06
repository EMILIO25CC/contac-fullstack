package com.contac.config;


import com.contac.entity.Usuario;
import com.contac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default.username}")
    private String defaultUsername;

    @Value("${app.admin.default.password}")
    private String defaultPassword;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByUsername(defaultUsername)) {
            Usuario admin = Usuario.builder()
                    .username(defaultUsername)
                    .password(passwordEncoder.encode(defaultPassword))
                    .rol("ADMIN")
                    .build();

            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN por defecto creado: " + defaultUsername);
        }
    }
}