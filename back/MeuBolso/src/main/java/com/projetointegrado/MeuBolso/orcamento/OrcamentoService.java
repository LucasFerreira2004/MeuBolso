package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;
import com.projetointegrado.MeuBolso.orcamento.exception.CategoriaOrcamentoNaoEncontradaException;
import com.projetointegrado.MeuBolso.orcamento.exception.CategoriaRepetidaException;
import com.projetointegrado.MeuBolso.orcamento.exception.OrcamentoNaoEncontradoException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrcamentoService implements IOrcamentoService{

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public List<OrcamentoDTO> findAll(String usuarioId) {
        List<Orcamento> result = orcamentoRepository.findAllByUsuario(usuarioId);
        return result.stream().map(OrcamentoDTO::new).toList();
    }

    @Transactional
    public OrcamentoDTO findById(Long id, String usuarioId) {
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(OrcamentoNaoEncontradoException::new);

        if (!orcamento.getUsuario().getId().equals(usuarioId))
            throw new AcessoNegadoException();

        return new OrcamentoDTO(orcamento);
    }

    @Transactional
    public OrcamentoDTO save(String usuarioId, OrcamentoPostDTO orcamento) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNaoEncontradoException::new);
        Categoria categoria = categoriaRepository.findById(orcamento.getIdCategoria())
                .orElseThrow(CategoriaOrcamentoNaoEncontradaException::new);

        if (orcamentoRepository.findOrcamentoByCategoria(categoria.getId()).isPresent())
            throw new CategoriaRepetidaException();
        if (!categoria.getAtiva())
            throw new CategoriaOrcamentoNaoEncontradaException();
        if (!categoria.getUsuario().getId().equals(usuarioId))
            throw new AcessoNegadoException();

        Orcamento newOrcamento = new Orcamento(categoria, orcamento.getMesAno(), orcamento.getValorEstimado(), usuario);
        return new OrcamentoDTO(orcamentoRepository.save(newOrcamento));
    }

    @Transactional
    public OrcamentoDTO update(Long id, OrcamentoPostDTO orcamento, String usuarioId) {
        Orcamento orcamentoAtual = orcamentoRepository.findById(id).orElseThrow(OrcamentoNaoEncontradoException::new);
        Categoria categoria = categoriaRepository.findById(orcamento.getIdCategoria()).orElseThrow(CategoriaOrcamentoNaoEncontradaException::new);

        if (!categoria.getAtiva())
            throw new CategoriaOrcamentoNaoEncontradaException();
        if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(usuarioId))
            throw new AcessoNegadoException();
        if (!orcamentoAtual.getUsuario().getId().equals(usuarioId))
            throw new AcessoNegadoException();
        if (orcamentoRepository.findOrcamentoByCategoria(categoria.getId()).isPresent())
            throw new CategoriaRepetidaException(categoria.getNome());

        orcamentoAtual.setValorEstimado(orcamento.getValorEstimado());
        orcamentoAtual.setMesAno(orcamento.getMesAno());
        orcamentoAtual.setCategoria(categoria);

        return new OrcamentoDTO(orcamentoRepository.save(orcamentoAtual));
    }

    @Transactional
    public void deleteById(String usuarioId, Long id) {
        Orcamento orcamentoEntity = orcamentoRepository.findById(id).orElseThrow(OrcamentoNaoEncontradoException::new);

        if(!Objects.equals(usuarioRepository.findById(usuarioId).orElse(null), orcamentoEntity.getUsuario()))
            throw new AcessoNegadoException();

        orcamentoRepository.delete(orcamentoEntity);
    }
}
