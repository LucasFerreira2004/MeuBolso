package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaRepository;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.meta.Meta;
import com.projetointegrado.MeuBolso.meta.MetaRepository;
import com.projetointegrado.MeuBolso.meta.MetaValidateService;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.TransacaoValidateService;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.exceptions.SaldoInsuficienteException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TransacaoDTO save(String userId, TransacaoMetaSaveDTO dto, Long metaId) {
        Transacao transacao = saveAndValidate(userId, null, dto, metaId);
        return new TransacaoDTO(transacao);
    }

    private Transacao saveAndValidate(String userId, Long id, TransacaoMetaSaveDTO dto, Long metaId) {
        Conta conta = contaValidateService.validateAndGet(dto.getContaId(), userId,
                new EntidadeNaoEncontradaException("Conta nao encontrada"),
                new AcessoNegadoException());
        System.out.println("criaTransacaoMeta: conta validade -> verificar meta");

        Meta meta = metaValidateService.validateAndGet(metaId, userId,
                new EntidadeNaoEncontradaException("Meta nao encontrada"),
                new AcessoNegadoException());
        System.out.println("criaTransacaoMeta: meta validade -> verificar saldo");

        Categoria categoria = categoriaValidateService.validateAndGet(dto.getCategoriaId(), userId,
                new EntidadeNaoEncontradaException("Meta nao encontrada"),
                new AcessoNegadoException());
        System.out.println("criaTransacaoMeta: meta validade -> verificar saldo");

        if (conta.getSaldo(dto.getData()).compareTo(dto.getValor()) < 0) {
            throw new SaldoInsuficienteException("{saldo}", "Saldo insuficiente na conta de origem");
        }
        System.out.println("criaTransacaoMeta: saldo validade -> criar transacao");

        // Cria a transação comum
        Transacao transacao = new Transacao(
                null,
                dto.getValor(),
                dto.getData(),
                dto.getTipoTransacao(),
                categoria,
                conta,
                null,
                ("meta: " + meta.getDescricao()),
                meta.getUsuario()
        );
        transacaoRepository.save(transacao);

        System.out.println("criaTransacaoMeta: transacao criada -> criar transacao meta");
        // Cria a transação de meta
        transacao.setTipo(TipoTransacao.RECEITA == dto.getTipoTransacao() ? TipoTransacao.DESPESA : TipoTransacao.RECEITA);

        TransacaoMeta transacaoMeta = new TransacaoMeta();
        transacaoMeta.setTransacao(transacao);
        transacaoMeta.setMeta(meta);
        transacaoMetaRepository.save(transacaoMeta);

        System.out.println("criaTransacaoMeta: transacao meta concluida");
        return transacao;
    }
}
