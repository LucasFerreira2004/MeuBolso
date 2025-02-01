package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
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
        Orcamento orcamento = saveAndValidate(usuarioId, null, orcamentoDto, orcamentoDto.getPeriodo().getMonth().getValue(), orcamentoDto.getPeriodo().getYear());
        return new OrcamentoDTO(orcamento);
    }

    @Transactional
    public OrcamentoDTO update(Long id, OrcamentoPostDTO orcamentoDto, String usuarioId) {
        Orcamento orcamento = saveAndValidate(usuarioId, id, orcamentoDto, orcamentoDto.getPeriodo().getMonth().getValue(), orcamentoDto.getPeriodo().getYear());
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
    public List<OrcamentoDTO> findOrcamentosByPeriodo(String usuarioId, LocalDate periodo) {
        System.out.println("OrcamentoService: chamando o atualizarValores");
        atualizacaoOrcamentoService.atualizarOrcamentos(usuarioId, periodo);

        System.out.println("OrcamentoService: buscar orcamentos por periodo");
        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioAndPeriodo(usuarioId, periodo.getYear(), periodo.getMonth().getValue());

        System.out.println("OrcamentoService: retornando orcamentos");
        return orcamentos.stream().map(OrcamentoDTO::new).toList();
    }

    private Orcamento saveAndValidate(String usuarioId, Long id, OrcamentoPostDTO orcamentoDTO, Integer mes, Integer ano) {
        // Valida o usuário
        Usuario usuario = usuarioValidateService.validateAndGet(usuarioId,
                new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado a partir do token"));
        System.out.println("saveAndValidate: usuario validado -> categoria");

        // Valida a categoria
        Categoria categoria = categoriaValidateService.validateAndGet(orcamentoDTO.getIdCategoria(), usuarioId,
                new EntidadeNaoEncontradaException("{id}", "categoria nao encontrada"),
                new AcessoNegadoException());
        System.out.println("saveAndValidate: categoria -> validacao unicidade");

        // Valida se existe outro orçamento com a mesma categoria, do mesmo usuário para o mesmo período
        orcamentoValidateService.validateSamePeriod(categoria, usuario, mes, ano,
                new OrcamentoDuplicadoException(),
                new CategoriaOrcamentoException());
        System.out.println("saveAndValidate: validacao unicidade -> construcao do orcamento");

        Orcamento orcamento;
        if (id != null) {
            // Busca o orçamento existente para atualizar
            orcamento = orcamentoRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("{id}", "Orcamento não encontrada para atualização"));

            // Atualiza os campos do orçamento existente
            orcamento.setValorEstimado(orcamentoDTO.getValorEstimado());
            orcamento.setCategoria(categoria);
            orcamento.setMes(mes);
            orcamento.setAno(ano);
        } else {
            // Cria novo orçamento se for um save
            orcamento = new Orcamento(categoria, mes, ano, orcamentoDTO.getValorEstimado(), usuario);
        }

        System.out.println("saveAndValidate: construcao de orcamento -> salvamento");
        System.out.println(orcamento);
        return orcamentoRepository.save(orcamento); // Salva o orçamento no banco de dados
    }
}
