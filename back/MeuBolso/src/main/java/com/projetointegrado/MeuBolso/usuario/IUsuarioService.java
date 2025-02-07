package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    public UsuarioDTO save(UsuarioDTO usuarioDTO);
    public String getUsuarioLogadoId();
}
