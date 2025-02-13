package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;

public interface IUsuarioService {
    public UsuarioSaveDTO save(UsuarioSaveDTO usuarioSaveDTO);
    public String getUsuarioLogadoId();
    public UsuarioDTO findById(String id);
    public UsuarioSaveDTO update(String userId, UsuarioSaveDTO usuarioSaveDTO);
}
