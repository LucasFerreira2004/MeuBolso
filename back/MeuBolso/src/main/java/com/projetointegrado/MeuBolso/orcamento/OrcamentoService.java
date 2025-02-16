package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;
import com.projetointegrado.MeuBolso.orcamento.exception.CategoriaOrcamentoException;
import com.projetointegrado.MeuBolso.orcamento.exception.OrcamentoDuplicadoException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import jakarta.transaction.Transactional;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Autowired
    private AtualizacaoOrcamentoService atualizacaoOrcamentoService;
    @Autowired
    private CategoriaRepository categoriaRepository;

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
        Orcamento orcamento = saveAndValidate(usuarioId, null, orcamentoDto,
                orcamentoDto.getPeriodo().getMonth().getValue(),
                orcamentoDto.getPeriodo().getYear());
        return new OrcamentoDTO(orcamento);
    }

    @Transactional
    public OrcamentoDTO update(Long id, OrcamentoPostDTO orcamentoDto, String usuarioId) {
        Orcamento orcamento = updateAndValidate(usuarioId, id, orcamentoDto,
                orcamentoDto.getPeriodo().getMonth().getValue(),
                orcamentoDto.getPeriodo().getYear());
        return new OrcamentoDTO(orcamento);
    }

    @Transactional
    public OrcamentoDTO deleteById(String usuarioId, Long id) {
        Orcamento orcamento = orcamentoValidateService.validateAndGet(id, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "Orcamento nao encontrado"),
                new AcessoNegadoException());
        orcamentoRepository.delete(orcamento);
        return new OrcamentoDTO(orcamento);
    }

    @Transactional
    public List<OrcamentoDTO> findOrcamentosByPeriodo(String usuarioId, Integer ano, Integer mes) {
        atualizacaoOrcamentoService.atualizarOrcamentos(usuarioId, ano, mes);
        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioAndPeriodo(usuarioId, ano, mes);
        return orcamentos.stream().map(OrcamentoDTO::new).toList();
    }

    @Transactional
    public Orcamento saveAndValidate(String usuarioId, Long id, OrcamentoPostDTO orcamentoDTO, Integer mes, Integer ano) {
        // Valida o usuario e a categoria (supondo que esses métodos já fazem a validação)
        Usuario usuario = usuarioValidateService.validateAndGet(usuarioId,
                new EntidadeNaoEncontradaException("{token}", "usuário não encontrado a partir do token"));
        Categoria categoria = categoriaValidateService.validateAndGet(orcamentoDTO.getIdCategoria(), usuarioId,
                new EntidadeNaoEncontradaException("{id}", "categoria não encontrada"),
                new AcessoNegadoException());
        orcamentoValidateService.validateCategoria(categoria, new CategoriaOrcamentoException());

        // Valida a unicidade apenas para novo cadastro (id == null)
        orcamentoValidateService.validateSamePeriod(categoria, usuarioId, mes, ano, id, new OrcamentoDuplicadoException());

        // Cria a nova orcamento
        Orcamento orcamento = new Orcamento(null, categoria, mes, ano, orcamentoDTO.getValorEstimado(), usuario);

        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public Orcamento updateAndValidate(String usuarioId, Long id, OrcamentoPostDTO orcamentoDTO, Integer mes, Integer ano) {
        // Primeiro, valida que o orçamento existe e pertence ao usuario
        Orcamento orcamentoExistente = orcamentoValidateService.validateAndGet(id, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "Orçamento não encontrado para atualização"),
                new AcessoNegadoException("Acesso negado para atualizar este orçamento."));

        Categoria categoria = categoriaValidateService.validateAndGet(orcamentoDTO.getIdCategoria(), usuarioId,
                new EntidadeNaoEncontradaException("{id}", "categoria não encontrada"),
                new AcessoNegadoException());
        orcamentoValidateService.validateCategoria(categoria, new CategoriaOrcamentoException());

        // Valida que, ao atualizar, não há duplicidade com outro orçamento
        orcamentoValidateService.validateSamePeriod(categoria, usuarioId, mes, ano, id, new OrcamentoDuplicadoException());

        // Atualiza os campos do orçamento existente
        orcamentoExistente.setValorEstimado(orcamentoDTO.getValorEstimado());
        orcamentoExistente.setMes(mes);
        orcamentoExistente.setAno(ano);
        orcamentoExistente.setCategoria(categoria); // Atualiza a categoria, se necessário

        return orcamentoRepository.save(orcamentoExistente);
    }

}
