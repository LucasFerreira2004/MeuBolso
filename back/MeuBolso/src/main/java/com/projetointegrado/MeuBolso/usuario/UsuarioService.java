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
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO login(String email, String senha) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(senha)) {
            throw new SenhaIncorretaException("Senha incorreta");
        }

        // Converter para DTO
        return new UsuarioDTO(usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }
}