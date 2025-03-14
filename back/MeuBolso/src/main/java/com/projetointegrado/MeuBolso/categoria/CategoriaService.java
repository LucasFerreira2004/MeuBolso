package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;

import com.projetointegrado.MeuBolso.categoria.exception.AtivaInalteradaException;
import com.projetointegrado.MeuBolso.categoria.exception.CategoriaNaoEncontrada;
import com.projetointegrado.MeuBolso.categoria.exception.ModificacaoCategoriaInternaException;
import com.projetointegrado.MeuBolso.categoria.exception.NomeCadastradoException;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioValidateService usuarioValidateService;

    @Autowired
    CategoriaValidateService categoriaValidateService;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAllAtivas(String usuarioId) {
        List<Categoria> result = categoriaRepository.findAllAtivas(usuarioId);
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAllArquivadas(String usuarioId) {
        List<Categoria> result = categoriaRepository.findAllArquivadas(usuarioId);
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findById(String usuarioId, Long id) {
        Categoria categoria = categoriaValidateService.validateAndGet(id, usuarioId,
                new CategoriaNaoEncontrada("/{id}", "categoria não encontrada"), new AcessoNegadoException());
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
        Categoria categoria = categoriaRepository.findByName(usuarioId, dto.getNome());
        if (categoria != null && categoria.getInternaSistema())
            throw new NomeCadastradoException("já existe uma categoria interna do sistema com esse nome");
        else if(categoria != null)
            throw new NomeCadastradoException();

        Usuario usuario = usuarioValidateService.validateAndGet(usuarioId, new EntidadeNaoEncontradaException("{token}", "usuario não controdaco"));

        Categoria novaCategoria = new Categoria(null, dto.getNome(), dto.getTipo(), dto.getCor(), true, usuario);
        return new CategoriaDTO(categoriaRepository.save(novaCategoria));
    }

    @Transactional
    public CategoriaDTO update(String usuarioId, Long id, CategoriaSaveDTO dto) {
        Categoria categoria = categoriaValidateService.validateAndGet(id, usuarioId,
                new CategoriaNaoEncontrada("/{id}", "categoria não encontrada"), new AcessoNegadoException());
        if (categoria.getInternaSistema())
            throw new ModificacaoCategoriaInternaException();

        Categoria categoriaDeMesmoNome = categoriaRepository.findByName(usuarioId, dto.getNome());
        if (categoriaDeMesmoNome != null && !categoriaDeMesmoNome.getId().equals(id) && categoriaDeMesmoNome.getInternaSistema())
            throw new NomeCadastradoException("já existe uma categoria interna do sistema com esse nome");
        else if (categoriaDeMesmoNome != null && !categoriaDeMesmoNome.getId().equals(id))
            throw new NomeCadastradoException();

        Usuario usuario = usuarioValidateService.validateAndGet(usuarioId, new EntidadeNaoEncontradaException("{token}", "usuario não controdaco"));
        Categoria novaCategoria = new Categoria(id, dto.getNome(), dto.getTipo(), dto.getCor(), true, usuario);

        categoriaRepository.save(novaCategoria);
        return new CategoriaDTO(novaCategoria);
    }

    @Transactional
    public CategoriaDTO atualizarStatusAtiva(String usuarioId, Long id, Boolean ativa) {
        Categoria categoria = categoriaValidateService.validateAndGet(id, usuarioId,
                new CategoriaNaoEncontrada("/{id}", "categoria não encontrada"), new AcessoNegadoException());
        if (categoria.getInternaSistema())
            throw new ModificacaoCategoriaInternaException();
        if (ativa.equals(categoria.getAtiva()))
            throw new AtivaInalteradaException("ativa");
        categoria.setAtiva(ativa);
        categoriaRepository.save(categoria);
        return new CategoriaDTO(categoria);
    }

}
