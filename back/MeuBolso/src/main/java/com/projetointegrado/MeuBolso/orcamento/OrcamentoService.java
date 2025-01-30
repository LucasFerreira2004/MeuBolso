package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrcamentoService implements IOrcamentoService{

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Autowired
    private CategoriaValidateService categoriaValidateService;

    @Autowired
    private OrcamentoValidateService orcamentoValidateService;

    @Transactional
    public List<OrcamentoDTO> findAll(String usuarioId) {
        List<Orcamento> result = orcamentoRepository.findAllByUsuario(usuarioId);
        return result.stream().map(OrcamentoDTO::new).toList();
    }

    @Transactional
    public OrcamentoDTO findById(Long id, String usuarioId) {
        Orcamento orcamento = orcamentoValidateService.validateAndGet(id, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "Orcamento nao encontrado"),
                new AcessoNegadoException());
        return new OrcamentoDTO(orcamento);
    }

    @Transactional
    public OrcamentoDTO save(OrcamentoPostDTO orcamentoDto, String usuarioId) {
        Orcamento orcamento = saveAndValidate(usuarioId, null, orcamentoDto);
        return new OrcamentoDTO(orcamentoRepository.save(orcamento));
    }

    @Transactional
    public OrcamentoDTO update(Long id, OrcamentoPostDTO orcamentoDto, String usuarioId) {
        Orcamento orcamento = saveAndValidate(usuarioId, id, orcamentoDto);
        return new OrcamentoDTO(orcamentoRepository.save(orcamento));
    }

    @Transactional
    public OrcamentoDTO deleteById(String usuarioId, Long id) {
        Orcamento orcamento = orcamentoValidateService.validateAndGet(id, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "Orcamento nao encontrado"),
                new AcessoNegadoException());
        orcamentoRepository.delete(orcamento);
        return new OrcamentoDTO(orcamento);
    }

    private Orcamento saveAndValidate(String usuarioId, Long id, OrcamentoPostDTO orcamentoDTO) {
        Usuario usuario = usuarioValidateService.validateAndGet(usuarioId,
                new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado a partir do token"));
        System.out.println("saveAndValidate: usuario validado -> categoria");

        Categoria categoria = categoriaValidateService.validateAndGet(orcamentoDTO.getIdCategoria(), usuarioId,
                new EntidadeNaoEncontradaException("{id}", "categoria nao encontrada"),
                new AcessoNegadoException());
        System.out.println("saveAndValidate: categoria -> construcao de orcamento");

        // Devo tratar aqui se existe outra orcamento com a mesma categoria, do mesmo usuario para o mesmo periodo

        Orcamento orcamento;
        if (id != null) {
            // Busca a orcamento existente para atualizar
            orcamento = orcamentoRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("{id}", "Orcamento não encontrada para atualização"));

            // Atualiza os campos da orcamento existente
            orcamento.setValorEstimado(orcamentoDTO.getValorEstimado());
            orcamento.setCategoria(categoria);
            orcamento.setMes(orcamentoDTO.getMes());
            orcamento.setAno(orcamentoDTO.getAno());
        } else {
            // Cria nova orcamento se for um save
            orcamento = new Orcamento(categoria, orcamentoDTO.getMes(), orcamentoDTO.getAno(), orcamentoDTO.getValorEstimado(), usuario);
        }

        System.out.println("saveAndValidate: construcao de orcamento -> salvamento");
        System.out.println(orcamento);
        return orcamentoRepository.save(orcamento);
    }
}
