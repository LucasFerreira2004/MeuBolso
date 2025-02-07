package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.categoria.exception.AtivaInalteradaException;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.ContaValidateService;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoRecorrenteDTO;
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
    public List<TransacaoRecorrenteDTO> findAll(String userId){
        return transacaoRecorrenteRepository.findAllByUsuario(userId).stream().map(x -> new TransacaoRecorrenteDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public List<TransacaoRecorrenteDTO> findAllArquivadas(String userId){
        return transacaoRecorrenteRepository.findAllArquivadasByUsuario(userId).stream().map(t -> new TransacaoRecorrenteDTO(t)).toList();
    }

    @Transactional(readOnly = true)
    public TransacaoRecorrenteDTO findById(String userId, Long id){
        TransacaoRecorrente transacao = transacaoRecorrenteValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("/{id}", "TransacaoRecorrente nao encontrada"), new AcessoNegadoException());
        return new TransacaoRecorrenteDTO(transacao);
    }

    @Transactional
    public TransacaoRecorrenteDTO save(String userId, ITransacaoRecorrenteDTO dto){
        TransacaoRecorrente transacaoRecorrente = saveAndValidate(userId, dto);

        return new TransacaoRecorrenteDTO(transacaoRecorrente);
    }

    @Transactional
    public TransacaoRecorrenteDTO update(String userId, Long id, ITransacaoRecorrenteDTO dto){
        if (transacaoRecorrenteRepository.findById(id).isEmpty())
            throw new EntidadeNaoEncontradaException("/{id}", "TransacaoRecorrente nao encontrada");

        TransacaoRecorrente fixa = saveAndValidate(userId, dto);
        return new TransacaoRecorrenteDTO(fixa);
    }

    @Transactional
    public TransacaoRecorrenteDTO delete(String userId, Long id) {
        TransacaoRecorrente transacaoRecorrente = transacaoRecorrenteValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("/{id}", "TransacaoRecorrente nao encontrada"), new AcessoNegadoException());
        TransacaoRecorrenteDTO dto = new TransacaoRecorrenteDTO(transacaoRecorrente);
        transacaoRecorrenteRepository.delete(transacaoRecorrente);
        return dto;
    }


    private TransacaoRecorrente saveAndValidate(String userId, ITransacaoRecorrenteDTO dto) {
        Conta conta = contaValidateService.validateAndGet(dto.contaId(), userId,
                new EntidadeNaoEncontradaException("contaId: ", "Conta não encontrada"), new AcessoNegadoException());

        Categoria categoria = categoriaValidateService.validateAndGet(dto.categoriaId(), userId,
                new EntidadeNaoEncontradaException("categoriaId", "Categoria nao encontrada"), new AcessoNegadoException());

        Usuario usuario = usuarioValidateService.validateAndGet(userId, new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado"));

        transacaoRecorrenteValidateService.validateTipo(userId, TipoTransacao.valueOf(dto.tipoTransacao()), dto.categoriaId());

        TransacaoRecorrente transacaoRecorrente = new TransacaoRecorrente(null, dto.valor(), TipoTransacao.valueOf(dto.tipoTransacao()), dto.data(),
                dto.descricao(), conta, categoria, Periodicidade.valueOf(dto.periodicidade()), usuario, dto.qtdParcelas(), dto.tipoRepeticao());

        return transacaoRecorrenteRepository.save(transacaoRecorrente);
    }

    @Transactional
    public TransacaoRecorrenteDTO atualizarStatusAtiva(String usuarioId, Long id, Boolean ativa) {
       TransacaoRecorrente recorrente = transacaoRecorrenteValidateService.validateAndGet(id, usuarioId, new EntidadeNaoEncontradaException("{/id", "transacao recorrente nao encontrada"),
               new AcessoNegadoException());
        if (ativa.equals(recorrente.getAtiva()))
            throw new AtivaInalteradaException("impossível arquivar uma transcao recorrente já arquivada ou desarquivar uma transacao recorrente já desarquivada", "ativa");
        recorrente.setAtiva(ativa);
        transacaoRecorrenteRepository.save(recorrente);
        return new TransacaoRecorrenteDTO(recorrente);
    }
}
