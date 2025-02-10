package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.meta.*;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.exceptions.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // Valida a conta
        Conta conta = contaValidateService.validateAndGet(dto.getContaId(), userId,
                new EntidadeNaoEncontradaException("Conta nao encontrada"),
                new AcessoNegadoException());
        System.out.println("criaTransacaoMeta: conta validada -> verificar meta");

        // Valida a meta
        Meta meta = metaValidateService.validateAndGet(metaId, userId,
                new EntidadeNaoEncontradaException("Meta nao encontrada"),
                new AcessoNegadoException());
        System.out.println("criaTransacaoMeta: meta validada -> verificar saldo");

        // Valida a categoria
        Categoria categoria = categoriaValidateService.validateAndGet(dto.getCategoriaId(), userId,
                new EntidadeNaoEncontradaException("Categoria nao encontrada"),
                new AcessoNegadoException());
        System.out.println("criaTransacaoMeta: categoria validada -> verificar saldo");

        // Verifica se a conta possui saldo suficiente
        if (conta.getSaldo(dto.getData()).compareTo(dto.getValor()) < 0) {
            throw new SaldoInsuficienteException("{saldo}", "Saldo insuficiente na conta de origem");
        }
        System.out.println("criaTransacaoMeta: saldo validado -> criar transacao");

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
                meta.getUsuario()
        );
        transacaoRepository.save(transacao);

        System.out.println("criaTransacaoMeta: transacao criada -> preparar transacao meta");

        // Cria a TransacaoMeta
        TransacaoMeta transacaoMeta = new TransacaoMeta();
        transacaoMeta.setTransacao(transacao);
        transacaoMeta.getTransacao().setTipo(dto.getTipoTransacao());

        // Adiciona a transação à meta, que por cascata salvará também a TransacaoMeta.
        System.out.println("criaTransacaoMeta: transacao meta criada -> adicionar a uma meta");
        meta.adicionarTransacao(transacaoMeta);

        System.out.println("criaTransacaoMeta: adicionar a uma meta -> atualizar a meta");
        transacaoMetaRepository.save(transacaoMeta);
        System.out.println("Valor investido antes de salvar: " + meta.getValorInvestido());
        metaRepository.save(meta);

        System.out.println("criaTransacaoMeta: transacao meta concluída e meta atualizada");

        return transacaoMeta;
    }
}
