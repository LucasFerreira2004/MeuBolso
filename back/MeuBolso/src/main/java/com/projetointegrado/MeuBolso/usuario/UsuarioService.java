package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.categoria.CriarCategoriasIniciaisService;
import com.projetointegrado.MeuBolso.categoria.ICategoriaService;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.exception.EmailJaCadastradoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CriarCategoriasIniciaisService criarCategoriasIniciaisService;

    @Transactional
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()) != null)
            throw new EmailJaCadastradoException();
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());
        Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), encryptedPassword);

        usuario = usuarioRepository.save(usuario);

        criarCategoriasIniciaisService.criarCategorias(usuario.getId());
        return new UsuarioDTO(usuario);
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

    public String getUsuarioLogadoId(){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario.getId();
    }
}