package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.TransacaoRepeticaoService;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TransacaoService implements ITransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaValidateService contaValidateService;

    @Autowired
    private CategoriaValidateService categoriaValidateService;

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Autowired
    private TransacaoValidateService transacaoValidateService;

    //temporário
    @Autowired
    private TransacaoRepeticaoService transacaoRepeticaoService;

    @Transactional(readOnly = true)
    public TransacaoDTO findById(String userId, Long id){
        Transacao transacao = transacaoRepository.findById(id).orElse(null);
        if (transacao == null)
            throw new EntidadeNaoEncontradaException("/{id}", "Transacao nao encontrada");
        if (!transacao.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();
        return new TransacaoDTO(transacao);
    }
    @Transactional(readOnly = true)
    public List<TransacaoDTO> findAllByMonth(String userId, LocalDate data) {
        LocalDate dataInicio = data.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataFim = data.with(TemporalAdjusters.lastDayOfMonth());
        List<Transacao> transacoes = transacaoRepository.findAllInRange(dataInicio, dataFim, userId);
        List<TransacaoDTO> transacaoDTOs = transacoes.stream().map(transacao -> new TransacaoDTO(transacao)).toList();
        return transacaoDTOs;
    }

    @Transactional
    public TransacaoDTO save(String userId, TransacaoSaveDTO dto) {
        Transacao transacao = saveAndValidate(userId, null, dto);
        return new TransacaoDTO(transacao);
    }

    @Transactional
    public TransacaoDTO update(String userId, Long id, TransacaoSaveDTO dto) {
        Transacao transacao = saveAndValidate(userId, id, dto);
        return new TransacaoDTO(transacao);
    }

    private Transacao  saveAndValidate(String userId, Long id, TransacaoSaveDTO dto) {
    Conta conta = contaValidateService.validateAndGet(dto.getContaId(), userId,
            new EntidadeNaoEncontradaException("contaId", "conta nao encontrada"), new AcessoNegadoException());

    Categoria categoria = categoriaValidateService.validateAndGet(dto.getCategoriaId(), userId,
            new EntidadeNaoEncontradaException("categoriaId", "Categoria nao encontrada"), new AcessoNegadoException());

    Usuario usuario = usuarioValidateService.validateAndGet(userId, new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado a partir do token"));
    System.out.println("TransacaoService -> saveAndValidate : chegou ao fim das checagens");

    Transacao transacao = new Transacao(id, dto.getValor(), dto.getData(), dto.getTipoTransacao(),
                categoria, conta, dto.getComentario(), dto.getDescricao(), usuario);
    System.out.println(transacao);
    return transacaoRepository.save(transacao);
    }

    public TransacaoDTO delete(String userId, Long id){
        Transacao transacao = transacaoValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("{/id}", "transacao nao encontrada"), new AcessoNegadoException());
        transacaoRepository.delete(transacao);
        return new TransacaoDTO(transacao);

    }
}
