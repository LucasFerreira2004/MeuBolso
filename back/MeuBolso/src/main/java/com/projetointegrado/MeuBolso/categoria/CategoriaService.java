package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import com.projetointegrado.MeuBolso.categoria.exceptions.AcessoCategoriaNegadoException;
import com.projetointegrado.MeuBolso.categoria.exceptions.CategoriaNaoEncontrada;
import com.projetointegrado.MeuBolso.categoria.exceptions.NomeCadastradoException;
import com.projetointegrado.MeuBolso.categoria.exceptions.TipoCategoriaNaoEspecificado;
import com.projetointegrado.MeuBolso.conta.exception.AcessoContaNegadoException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findCategoria(String usuarioId) {
        List<Categoria> result = categoriaRepository.findAllAtivas(usuarioId);
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findCategoriaById(String usuarioId, Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null) {
            throw new CategoriaNaoEncontrada();
        }
        if (!categoria.getUsuario().getId().equals(usuarioId))
            throw new AcessoCategoriaNegadoException();
        return new CategoriaDTO(categoria);
    }

    @Transactional
    public List<CategoriaDTO> findAllByReceita(String usuarioId){
        List<Categoria> result = categoriaRepository.findAllByReceita(usuarioId);
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional
    public List<CategoriaDTO> findAllByDespesa(String usuarioId){
        List<Categoria> result = categoriaRepository.findAllByDespesa(usuarioId);
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional
    public CategoriaDTO save(String usuarioId, CategoriaSaveDTO dto) {
        if (categoriaRepository.findByName(usuarioId, dto.getNome()) != null)
            throw new NomeCadastradoException();
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        Categoria categoria = new Categoria(null, dto.getNome(), dto.getTipo(), dto.getCor(), true, usuario);
        return new CategoriaDTO(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaDTO update(String usuarioId, Long id, CategoriaSaveDTO dto) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null) {
            throw new CategoriaNaoEncontrada();
        }
        if (!categoria.getUsuario().getId().equals(usuarioId))
            throw new AcessoCategoriaNegadoException();

        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        Categoria novaCategoria = new Categoria(id, dto.getNome(), dto.getTipo(), dto.getCor(), true, usuario);
        categoriaRepository.save(novaCategoria);
        return new CategoriaDTO(novaCategoria);
    }

    @Transactional
    public CategoriaDTO arquivar(String usuarioId, Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null) {
            throw new CategoriaNaoEncontrada();
        }
        if (!categoria.getUsuario().getId().equals(usuarioId))
            throw new AcessoCategoriaNegadoException();
        categoriaRepository.arquivarById(usuarioId, id);
        return new CategoriaDTO(categoria);
    }
}
