package com.projetointegrado.MeuBolso.usuario.dto;

import com.projetointegrado.MeuBolso.usuario.Usuario;

public record UsuarioDTO (
        String nome,
        String email
) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getNome(), usuario.getEmail());
    }
}
