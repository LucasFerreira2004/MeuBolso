package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.exception.SenhaIncorretaException;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioExistenteException;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        List<Usuario> list = usuarioRepository.findAll();

        return list.stream().map(UsuarioDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(String id) {
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return new UsuarioDTO(usuario);
    }

}