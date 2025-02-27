package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IUsuarioService {
    public UsuarioDTO save(UsuarioSaveDTO usuarioSaveDTO);
    public String getUsuarioLogadoId();
    public UsuarioDTO findById(String id);
    public UsuarioDTO update(String userId, UsuarioSaveDTO usuarioSaveDTO, MultipartFile img);
}
