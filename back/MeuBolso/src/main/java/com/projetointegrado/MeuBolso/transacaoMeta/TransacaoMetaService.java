package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.meta.*;
import com.projetointegrado.MeuBolso.transacao.OrigemTransacao;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.exceptions.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.projetointegrado.MeuBolso.meta.notifications.ToastNotificationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoMetaService implements ITransacaoMetaService {

    @Autowired
    private TransacaoMetaRepository transacaoMetaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private MetaValidateService metaValidateService;

    @Autowired
    private ContaValidateService contaValidateService;

    @Autowired
    private CategoriaValidateService categoriaValidateService;

    @Autowired
    private TransacaoMetaValidateService transacaoMetaValidateService;

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private ToastNotificationService toastNotificationService;

    @Transactional
    public List<TransacaoMetaDTO> findAll(String userId) {
        List<TransacaoMeta> transacoesMeta = transacaoMetaRepository.findAllByUsuario(userId);
        return transacoesMeta.stream().map(TransacaoMetaDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public List<TransacaoMetaDTO> findByMeta(Long metaId) {
        List<TransacaoMeta> transacoesMeta = transacaoMetaRepository.findAllByMeta(metaId);
        return transacoesMeta.stream().map(TransacaoMetaDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public TransacaoMetaDTO findById(String userId, Long idTransacao) {
        TransacaoMeta transacaoMeta = transacaoMetaValidateService.validateAndGet(idTransacao, userId,
                new EntidadeNaoEncontradaException("{id}", "transacaoMeta nao encontrado a partir do id"),
                new AcessoNegadoException());
        return new TransacaoMetaDTO(transacaoMeta);
    }

    @Transactional
    public TransacaoMetaDTO update(String userId, Long idTransacao, TransacaoMetaSaveDTO dto) {
        TransacaoMeta transacao = saveAndValidate(userId, idTransacao, dto, dto.getMetaId());
        return new TransacaoMetaDTO(transacao);
    }

    @Transactional
    public TransacaoMetaDTO save(String userId, TransacaoMetaSaveDTO dto) {
        TransacaoMeta transacao = saveAndValidate(userId, null, dto, dto.getMetaId());
        return new TransacaoMetaDTO(transacao);
    }

    @Transactional
    public TransacaoMetaDTO delete(String userId, Long idTransacao) {
        TransacaoMeta transacaoMeta = transacaoMetaValidateService.validateAndGet(idTransacao, userId,
                new EntidadeNaoEncontradaException("{id}", "transacaoMeta nao encontrado a partir do id"),
                new AcessoNegadoException());
        transacaoMetaRepository.delete(transacaoMeta);
        return new TransacaoMetaDTO(transacaoMeta);
    }

    private TransacaoMeta saveAndValidate(String userId, Long id, TransacaoMetaSaveDTO dto, Long metaId) {
        Conta conta = contaValidateService.validateAndGet(dto.getContaId(), userId,
                new EntidadeNaoEncontradaException("Conta nao encontrada"),
                new AcessoNegadoException());

        Meta meta = metaValidateService.validateAndGet(metaId, userId,
                new EntidadeNaoEncontradaException("Meta nao encontrada"),
                new AcessoNegadoException());

        Categoria categoria = categoriaValidateService.validateAndGet(17L, userId,
                new EntidadeNaoEncontradaException("Categoria nao encontrada"),
                new AcessoNegadoException());

        if (conta.getSaldo(dto.getData()).compareTo(dto.getValor()) < 0) {
            throw new SaldoInsuficienteException("{saldo}", "Saldo insuficiente na conta de origem");
        }

        // Cria a transação comum com o tipo ajustado
        Transacao transacao = new Transacao(
                null,
                dto.getValor(),
                dto.getData(),
                dto.getTipoTransacao() == TipoTransacao.RECEITA ? TipoTransacao.DESPESA : TipoTransacao.RECEITA,
                categoria,
                conta,
                null,
                ("meta: " + meta.getDescricao()),
                meta.getUsuario(),
                OrigemTransacao.NORMAL
        );
        transacaoRepository.save(transacao);

        // Cria a TransacaoMeta
        TransacaoMeta transacaoMeta = new TransacaoMeta();
        transacaoMeta.setTransacao(transacao);
        transacaoMeta.getTransacao().setTipo(dto.getTipoTransacao());

        // Adiciona a transação à meta, que por cascata salvará também a TransacaoMeta.
        meta.adicionarTransacao(transacaoMeta);
        meta.adicionarObserver(toastNotificationService);
        meta.verificarThresholds();

        transacaoMetaRepository.save(transacaoMeta);
        metaRepository.save(meta);

        return transacaoMeta;
    }
}
