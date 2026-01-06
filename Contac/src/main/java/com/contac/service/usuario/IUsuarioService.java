package com.contac.service.usuario;

import com.contac.entity.Usuario;

public interface IUsuarioService {
    Usuario registrarUsuario(String username, String password);
    Usuario buscarPorUsername(String username);
    boolean existeUsuario(String username);
}