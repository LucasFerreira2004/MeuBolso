package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaRepository;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoFixaService implements ITransacaoFixaService {
    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    private TransacaoFixa  saveAndValidate(String userId, TransacaoFixaSaveDTO dto) {
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("contaId: ", "Conta não encontrada"));
        if (!conta.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("categoriaId", "Categoria nao encontrada"));
        if(!categoria.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        TransacaoFixa transacaoFixa = new TransacaoFixa(null, dto.getValor(), TipoTransacao.valueOf(dto.getTipoTransacao()), dto.getData(),
                dto.getDescricao(), conta, categoria, usuario);

        return transacaoFixaRepository.save(transacaoFixa);
    }
}
