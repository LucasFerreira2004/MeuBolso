package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.ITransacaoRecorrenteDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableScheduling
@Service
public class TransacaoRecorrenteService implements ITransacaoRecorrenteService {
    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    @Autowired
    private ContaValidateService contaValidateService;

    @Autowired
    private CategoriaValidateService categoriaValidateService;

    @Autowired
    private UsuarioValidateService usuarioValidateService;
    @Autowired
    private TransacaoRecorrenteValidateService transacaoRecorrenteValidateService;

    @Transactional(readOnly = true)
    public List<TransacaoFixaDTO> findAll(String userId){
        return transacaoRecorrenteRepository.findAllByUsuario(userId).stream().map(x -> new TransacaoFixaDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public TransacaoFixaDTO findById(String userId, Long id){
        TransacaoRecorrente transacao = transacaoRecorrenteValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("/{id}", "TransacaoRecorrente nao encontrada"), new AcessoNegadoException());
        return new TransacaoFixaDTO(transacao);
    }

    @Transactional
    public TransacaoFixaDTO save(String userId, ITransacaoRecorrenteDTO dto){
        TransacaoRecorrente transacaoRecorrente = saveAndValidate(userId, dto);

        return new TransacaoFixaDTO(transacaoRecorrente);
    }

    @Transactional
    public TransacaoFixaDTO update(String userId, Long id, ITransacaoRecorrenteDTO dto){
        if (transacaoRecorrenteRepository.findById(id).isEmpty())
            throw new EntidadeNaoEncontradaException("/{id}", "TransacaoRecorrente nao encontrada");

        TransacaoRecorrente fixa = saveAndValidate(userId, dto);
        return new TransacaoFixaDTO(fixa);
    }

    @Transactional
    public TransacaoFixaDTO delete(String userId, Long id) {
        TransacaoRecorrente transacaoRecorrente = transacaoRecorrenteValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("/{id}", "TransacaoRecorrente nao encontrada"), new AcessoNegadoException());
        TransacaoFixaDTO dto = new TransacaoFixaDTO(transacaoRecorrente);
        transacaoRecorrenteRepository.delete(transacaoRecorrente);
        return dto;
    }


    private TransacaoRecorrente saveAndValidate(String userId, ITransacaoRecorrenteDTO dto) {
        Conta conta = contaValidateService.validateAndGet(dto.contaId(), userId,
                new EntidadeNaoEncontradaException("contaId: ", "Conta não encontrada"), new AcessoNegadoException());

        Categoria categoria = categoriaValidateService.validateAndGet(dto.categoriaId(), userId,
                new EntidadeNaoEncontradaException("categoriaId", "Categoria nao encontrada"), new AcessoNegadoException());

        Usuario usuario = usuarioValidateService.validateAndGet(userId, new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado"));

        TransacaoRecorrente transacaoRecorrente = new TransacaoRecorrente(null, dto.valor(), TipoTransacao.valueOf(dto.tipoTransacao()), dto.data(),
                dto.descricao(), conta, categoria, Periodicidade.valueOf(dto.periodicidade()), usuario, dto.qtdParcelas(), dto.tipoRepeticao());

        return transacaoRecorrenteRepository.save(transacaoRecorrente);
    }
}
