package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaRepository;
import com.projetointegrado.MeuBolso.conta.exception.ContaNaoEncontradaException;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoService implements ITransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

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
    public List<TransacaoDTO> findAll(String userId) {
        List<Transacao> transacoes = transacaoRepository.findAllByUsuario(userId);
        return transacoes.stream().map(TransacaoDTO::new).toList();
    }

    @Transactional
    public TransacaoDTO save(String userId, TransacaoSaveDTO dto) {
        if (acessoDadosPermitido(userId, dto.getContaId(), dto.getCategoriaId())){
            Conta conta = contaRepository.findById(dto.getContaId()).orElse(null);
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
            transacaoRepository.save(new Transacao(null, dto.getValor(), dto.getDataTransacao(), dto.getTipoTransacao(), categoria, conta, dto.getComentario(), dto.getDescricao()));


        }
    }

    private boolean  acessoDadosPermitido(String userId, Long contaId, Long categoriaId){
        Conta conta = contaRepository.findById(contaId).orElse(null);
        if(conta == null)
            throw new EntidadeNaoEncontradaException("contaId: ", "Conta não encontrada");
        if (!conta.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
        if (categoria == null)
            throw new EntidadeNaoEncontradaException("categoriaId", "Categoria nao encontrada");
        if(!categoria.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();

        return true;
    }

}
