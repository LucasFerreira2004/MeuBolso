package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;

public interface IUsuarioService {
    public UsuarioDTO save(UsuarioSaveDTO usuarioSaveDTO);
    public String getUsuarioLogadoId();
    public UsuarioDTO findById(String id);
    public UsuarioDTO update(String userId, UsuarioSaveDTO usuarioSaveDTO);
}
