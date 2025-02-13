package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.categoria.CriarCategoriasIniciaisService;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
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

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Transactional
    public UsuarioDTO save(UsuarioSaveDTO usuarioSaveDTO) {
        if (usuarioRepository.findByEmail(usuarioSaveDTO.getEmail()) != null)
            throw new EmailJaCadastradoException();
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioSaveDTO.getSenha());
        Usuario usuario = new Usuario(usuarioSaveDTO.getNome(), usuarioSaveDTO.getEmail(), encryptedPassword);

        usuario = usuarioRepository.save(usuario);

        criarCategoriasIniciaisService.criarCategorias(usuario.getId());
        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO update(String userId, UsuarioSaveDTO usuarioSaveDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioSaveDTO.getSenha());
        Usuario usuario = new Usuario(usuarioSaveDTO.getNome(), usuarioSaveDTO.getEmail(), encryptedPassword, usuarioSaveDTO.getImg_url()); //mudar para passar que o getImag_url seja na verdade um byteCode da imamgem e eu possa gerar a url depois, creio que deve ser responsabilidade de outra classe para que possa ser reaproveitada.

        usuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }


    private List<UsuarioDTO> findAll() {
        List<Usuario> list = usuarioRepository.findAll();
        return list.stream().map(UsuarioDTO::new).toList();
    }

    public UsuarioDTO findById(String id) {
        Usuario usuario = usuarioValidateService.validateAndGet(id, new EntidadeNaoEncontradaException("{token}", "usuário não encontrado"));
        return new UsuarioDTO(usuario);
    }

    public String getUsuarioLogadoId(){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario.getId();
    }
}