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
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.TransacaoMensalService;
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

    //temporario
    @Autowired
    private TransacaoMensalService transacaoMensalService;

    @Transactional(readOnly = true)
    public ContaDTO findById(String idUsuario, Long id, LocalDate data) {
        Conta conta = contaValidateService.validateAndGet(id, idUsuario, new EntidadeNaoEncontradaException("/{id}", "conta nao encontrada"), new AcessoNegadoException());
        ContaDTO dto = new ContaDTO(conta);
        dto.setSaldo(conta.getSaldo(data));
        return dto;
    }
    @Transactional() //voltar pra read only depois
    public List<ContaDTO> findAll(String idUsuario, LocalDate data) {
        transacaoMensalService.gerarTransacoes(data, idUsuario);
        List<Conta> listConta = contaRepository.findAllByUsuario(idUsuario);
        List<ContaDTO> listDto = new ArrayList<>();
        for (Conta conta : listConta) {
            ContaDTO dto = new ContaDTO(conta);
            dto.setSaldo(conta.getSaldo(data));
            listDto.add(dto);
        }
        return listDto;
    }
    @Transactional(readOnly = true)
    public List<ContaMinDTO> findAllMin(String idUsuario, LocalDate data) {
        List<Conta> contas = contaRepository.findAllByUsuario(idUsuario);
        List<ContaMinDTO> listDto = new ArrayList<>();
        for (Conta conta : contas){
            ContaMinDTO dto = new ContaMinDTO(conta);
            dto.setSaldo(conta.getSaldo(data));
            listDto.add(dto);
        }
        return listDto;
    }

    @Transactional(readOnly = true)
    public SaldoTotalDTO getSaldo(String idUsuario, LocalDate data) {
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
        if (banco == null) throw new BancoNaoEncontradoException(); //adicionar verificação se banco pertence a outra pessoa
        if (usuario == null) throw new UsuarioNaoEncontradoException();

        if(contaRepository.findByDescricao(userID, dto.getDescricao()) != null) throw new DescricaoJaExistenteException();

        Conta conta = new Conta(null, tipo, banco, dto.getDescricao(), usuario);
        conta = contaRepository.save(conta);

        Categoria categoria = categoriaRepository.findByName(userID,"DepositoInicial*");
        if(categoria == null) throw new RuntimeException("categoria de nome DepositoInicial* não econtrada. ContaService -> save");
        try {
            TransacaoDTO transacaodto = transacaoService.save(userID, new TransacaoSaveDTO(dto.getSaldo(), dto.getData(), "RECEITA", categoria.getId(), conta.getId(), "DepositoInicial"));
            System.out.println("conta service -> save : transacaoDTO: " + transacaodto.toString());
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar transação", e);
        }
        System.out.println("Conta Service -> save");
        System.out.println("conta " + conta.toString());

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
        Conta conta = contaRepository.findById(id).orElse(null);

        if (tipo == null) throw new TipoContaNaoEncontradoException();
        if (banco == null) throw new BancoNaoEncontradoException();
        if (conta == null) throw new ContaNaoEncontradaException();
        if (!conta.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();

        if(contaRepository.findByDescricao(userId, dto.getDescricao()) != null
                && !conta.getDescricao().equals(dto.getDescricao())) throw new DescricaoJaExistenteException();
        //conta.setSaldo(dto.getSaldo());
        conta.setTipo_conta(tipo);
        conta.setBanco(banco);
        conta.setDescricao(dto.getDescricao());
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
        return new ContaDTO(contaRepository.save(conta));
    }

}
