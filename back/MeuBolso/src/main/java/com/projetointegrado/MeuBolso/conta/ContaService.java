package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.banco.BancoRepository;
import com.projetointegrado.MeuBolso.banco.exception.BancoNaoEncontradoException;
import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaRepository;
import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.conta.exception.*;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaRepository;
import com.projetointegrado.MeuBolso.tipoConta.exception.TipoContaNaoEncontradoException;
import com.projetointegrado.MeuBolso.transacao.ITransacaoService;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContaService implements IContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private TipoContaRepository tipoContaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ContaValidateService contaValidateService;

    @Autowired
    private ITransacaoService transacaoService;

    @Transactional
    public ContaDTO findById(String idUsuario, Long id, LocalDate data) {
        Conta conta = contaValidateService.validateAndGet(id, idUsuario, new EntidadeNaoEncontradaException("/{id}", "conta nao encontrada"), new AcessoNegadoException());
        ContaDTO dto = new ContaDTO(conta);
        dto.setSaldo(conta.getSaldo(data));
        return dto;
    }
    @Transactional
    public List<ContaDTO> findAll(String idUsuario, LocalDate data) {
        List<Conta> listConta = contaRepository.findAllByUsuario(idUsuario);
        List<ContaDTO> listDto = new ArrayList<>();
        for (Conta conta : listConta) { //implementar iterator?
            ContaDTO dto = new ContaDTO(conta);
            dto.setSaldo(conta.getSaldo(data));
            listDto.add(dto);
        }
        return listDto;
    }
    @Transactional
    public List<ContaMinDTO> findAllMin(String idUsuario, LocalDate data) {
        List<Conta> contas = contaRepository.findAllByUsuario(idUsuario);
        List<ContaMinDTO> listDto = new ArrayList<>();
        for (Conta conta : contas){ //implementar iterator?
            ContaMinDTO dto = new ContaMinDTO(conta);
            dto.setSaldo(conta.getSaldo(data));
            listDto.add(dto);
        }
        return listDto;
    }

    @Transactional
    public SaldoTotalDTO findSaldo(String idUsuario, LocalDate data) {
        BigDecimal saldo = new BigDecimal(0);
        List<Conta> contas = contaRepository.findAllByUsuario(idUsuario);
        for (Conta c : contas){
            saldo = saldo.add(c.getSaldo(data));
        }
        return new SaldoTotalDTO(saldo);
    }

    @Transactional
    public ContaDTO save(String userID, ContaPostDTO dto) {
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);
        Usuario usuario = usuarioRepository.findById(userID).orElse(null);

        if (tipo == null) throw new TipoContaNaoEncontradoException();
        if (banco == null) throw new BancoNaoEncontradoException();
        if (usuario == null) throw new UsuarioNaoEncontradoException();

        if(contaRepository.findByDescricao(userID, dto.getDescricao()) != null) throw new DescricaoJaExistenteException();

        Conta conta = new Conta(null, tipo, banco, dto.getDescricao(), dto.getSaldo(), usuario);
        conta = contaRepository.save(conta);
        return new ContaDTO(conta);
    }

    @Transactional
    public ContaDTO delete(Long id, String idUsuario) {
        Conta conta = contaRepository.findById(id).orElse(null);
        if (conta == null)
            throw new ContaNaoEncontradaException();
        if (!conta.getUsuario().getId().equals(idUsuario))
            throw new AcessoNegadoException();
        contaRepository.delete(conta);
        return new ContaDTO(conta);
    }

    @Transactional
    public ContaDTO update (Long id, ContaPutDTO dto, String userId) {
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);
        Conta conta = contaValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("/{id}", "conta nao encontrada"), new AcessoNegadoException());
        if (tipo == null) throw new TipoContaNaoEncontradoException();
        if (banco == null) throw new BancoNaoEncontradoException();

        if(contaRepository.findByDescricao(userId, dto.getDescricao()) != null && !conta.getDescricao().equals(dto.getDescricao()))
            throw new DescricaoJaExistenteException();

        conta.setTipo_conta(tipo);
        conta.setBanco(banco);
        conta.setDescricao(dto.getDescricao());
        createTransacaoUpdateSaldo(conta, dto, userId);

        return new ContaDTO(contaRepository.save(conta));
    }

    private void createTransacaoUpdateSaldo(Conta conta, ContaPutDTO dto, String userId){
        if (!Objects.equals(dto.getSaldo(), conta.getSaldo(dto.getData()))){
            Hibernate.initialize(conta.getTransacoes());
            BigDecimal valorTransacao = conta.getSaldo(dto.getData()).subtract(dto.getSaldo()).abs();
            TipoTransacao tipoTransacaoCorrecao;
            Categoria categoria;
            if (dto.getSaldo().compareTo(conta.getSaldo(dto.getData())) == 1) { //se saldo do dto for maior que saldo da conta
                tipoTransacaoCorrecao = TipoTransacao.RECEITA;
                categoria = categoriaRepository.findByName(userId, "ReajusteSaldoAumento*");
            }else {
                tipoTransacaoCorrecao = TipoTransacao.DESPESA;
                categoria = categoriaRepository.findByName(userId, "ReajusteSaldoDecremento*");
            }
            transacaoService.save(userId, new TransacaoSaveDTO(valorTransacao, dto.getData(), tipoTransacaoCorrecao.name(), categoria.getId(), conta.getId(), conta.getDescricao()));
        }
    }
}
