package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaRepository;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService implements ITransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

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

        List<TransacaoDTO> transacaoDTOs = transacoes.stream().map(transacao -> new TransacaoDTO(transacao)).toList();

        return transacaoDTOs;
    }

    @Transactional
    public TransacaoDTO save(String userId, TransacaoSaveDTO dto) {
        try{
            System.out.println("transacaoService -> save:");
            System.out.println(dto.toString());
            System.out.println("user id: " + userId);
            Transacao transacao = saveAndValidate(userId, dto);
            System.out.println("transacao: " + transacao);
            return new TransacaoDTO(transacao);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Transacao  saveAndValidate(String userId, TransacaoSaveDTO dto) {
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
    System.out.println("TransacaoService -> saveAndValidate : chegou ao fim das checagens");

    Transacao transacao = new Transacao(null, dto.getValor(), dto.getData(), dto.getTipoTransacao(),
                categoria, conta, dto.getComentario(), dto.getDescricao(), usuario);
    System.out.println(transacao);
    return transacaoRepository.save(transacao);
    }
}
