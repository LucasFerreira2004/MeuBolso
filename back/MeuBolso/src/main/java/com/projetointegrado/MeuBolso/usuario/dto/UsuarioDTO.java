package com.projetointegrado.MeuBolso.usuario.dto;

import com.projetointegrado.MeuBolso.usuario.Usuario;

public record UsuarioDTO (
        String nome,
        String email,
        String img_url
) {
    public UsuarioDTO(Usuario usuario){this(usuario.getNome(), usuario.getEmail(), usuario.getImg_url());}
}
