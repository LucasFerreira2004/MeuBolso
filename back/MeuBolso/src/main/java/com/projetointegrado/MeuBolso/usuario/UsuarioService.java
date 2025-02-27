package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.armazenamentoImagens.IStorageService;
import com.projetointegrado.MeuBolso.categoria.CriarCategoriasIniciaisService;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
import com.projetointegrado.MeuBolso.usuario.exception.EmailJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CriarCategoriasIniciaisService criarCategoriasIniciaisService;

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Qualifier("cloudinaryStorageService")
    @Autowired
    private IStorageService imgStorageService;

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

    @Transactional
    public UsuarioDTO update(String userId, UsuarioSaveDTO usuarioSaveDTO, MultipartFile img) {
        Usuario usuarioExistente = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));

        // Se a senha vier nula ou vazia, mantenha a senha antiga
        String senhaAtualizada;
        if (usuarioSaveDTO.getSenha() == null || usuarioSaveDTO.getSenha().isBlank()) {
            senhaAtualizada = usuarioExistente.getSenha(); // mantém senha antiga
        } else {
            senhaAtualizada = new BCryptPasswordEncoder().encode(usuarioSaveDTO.getSenha());
        }

        // Se houver imagem, faz o upload
        String imgUrl = null;
        if (img != null) {
            imgUrl = imgStorageService.uploadFile(img);
        }

        // Atualiza dados
        usuarioExistente.setNome(usuarioSaveDTO.getNome());
        usuarioExistente.setEmail(usuarioSaveDTO.getEmail());
        usuarioExistente.setSenha(senhaAtualizada);
        if (imgUrl != null) {
            usuarioExistente.setImg_url(imgUrl);
        }

        usuarioRepository.save(usuarioExistente);
        return new UsuarioDTO(usuarioExistente);
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