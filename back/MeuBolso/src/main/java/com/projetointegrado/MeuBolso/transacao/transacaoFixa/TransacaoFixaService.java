package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaRepository;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableScheduling
@Service
public class TransacaoFixaService implements ITransacaoFixaService {
    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;

    @Autowired
    private ContaValidateService contaValidateService;

    @Autowired
    private CategoriaValidateService categoriaValidateService;

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Transactional(readOnly = true)
    public List<TransacaoFixaDTO> findAll(String userId){
        return transacaoFixaRepository.findAllByUsuario(userId).stream().map(x -> new TransacaoFixaDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public TransacaoFixaDTO findById(String userId, Long id){
        TransacaoFixa transacao = transacaoFixaRepository.findById(id).orElse(null);
        if (transacao == null)
            throw new EntidadeNaoEncontradaException("/{id}", "TransacaoFixa nao encontrada");
        if (!transacao.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();
        return new TransacaoFixaDTO(transacao);
    }

    @Transactional
    public TransacaoFixaDTO save(String userId, TransacaoFixaSaveDTO dto){
        TransacaoFixa transacaoFixa = saveAndValidate(userId, dto);

        return new TransacaoFixaDTO(transacaoFixa);
    }

    @Transactional
    public TransacaoFixaDTO update(String userId, Long id, TransacaoFixaSaveDTO dto){
        if (transacaoFixaRepository.findById(id).isEmpty())
            throw new EntidadeNaoEncontradaException("/{id}", "TransacaoFixa nao encontrada");

        TransacaoFixa fixa = saveAndValidate(userId, dto);
        return new TransacaoFixaDTO(fixa);
    }

    @Transactional
    public TransacaoFixaDTO delete(String userId, Long id) {
        TransacaoFixa transacaoFixa = transacaoFixaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("/{id}", "TransacaoFixa nao encontrada"));
        if (!transacaoFixa.getUsuario().getId().equals(userId)) throw new AcessoNegadoException();
        TransacaoFixaDTO dto = new TransacaoFixaDTO(transacaoFixa);
        transacaoFixaRepository.delete(transacaoFixa);
        return dto;
    }


    private TransacaoFixa  saveAndValidate(String userId, TransacaoFixaSaveDTO dto) {
        Conta conta = contaValidateService.validateAndGet(dto.getContaId(), userId,
                new EntidadeNaoEncontradaException("contaId: ", "Conta não encontrada"), new AcessoNegadoException());

        Categoria categoria = categoriaValidateService.validateAndGet(dto.getCategoriaId(), userId,
                new EntidadeNaoEncontradaException("categoriaId", "Categoria nao encontrada"), new AcessoNegadoException());

        Usuario usuario = usuarioValidateService.validateAndGet(userId, new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado"));

        TransacaoFixa transacaoFixa = new TransacaoFixa(null, dto.getValor(), TipoTransacao.valueOf(dto.getTipoTransacao()), dto.getData(),
                dto.getDescricao(), conta, categoria, Periodicidade.valueOf(dto.getPeriodicidade()), usuario);

        return transacaoFixaRepository.save(transacaoFixa);
    }
}
