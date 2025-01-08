package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.banco.BancoRepository;
import com.projetointegrado.MeuBolso.categoria.exceptions.TipoCategoriaNaoEspecificado;
import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.conta.exception.*;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaRepository;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaService;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private TipoContaService tipoContaService;
    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private TipoContaRepository tipoContaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public ContaDTO findById(Long id) {
        Conta result = contaRepository.findById(id).orElse(null);
        String idUsuario = usuarioService.getUsuarioLogadoId();
        if (result == null){
            throw new IdContaNaoEncontradaException();
        }
        if (!result.getUsuario().getId().equals(idUsuario)) {
            throw new AcessoNegadoException("acesso a conta negado");
        }
        return new ContaDTO(result);
    }
    @Transactional(readOnly = true)
    public List<ContaDTO> findAll(String idUsuario) {

        List<Conta> result = contaRepository.findAllByUsuario(idUsuario);
        return result.stream().map(ContaDTO::new).toList();
    }
    @Transactional(readOnly = true)
    public List<ContaMinDTO> findAllMin(String idUsuario) {

        List<Conta> result = contaRepository.findAllByUsuario(idUsuario);
        return result.stream().map(ContaMinDTO::new).toList();
    }
    @Transactional
    public ContaDTO saveConta(String userID, ContaPostDTO dto) {
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);
        Usuario usuario = usuarioRepository.findById(userID).orElse(null);

        if (tipo == null) throw new IdTipoContaNaoEncontradoException();
        if (banco == null) throw new IdBancoNaoEncontradoException();
        if (usuario == null) throw new IdUsuarioNaoEncontradoException();

        Conta conta = new Conta(null, dto.getSaldo(), tipo, banco, usuario);
        return new ContaDTO(contaRepository.save(conta));
    }

    @Transactional
    public ContaDTO deleteConta(Long id, String idUsuario) {
        Conta conta = contaRepository.findById(id).orElse(null);
        if (conta == null) throw new IdContaNaoEncontradaException();
        if (!conta.getUsuario().getId().equals(idUsuario))
            throw new AcessoContaNegadoException();
        contaRepository.delete(conta);
        return new ContaDTO(conta);
    }

    @Transactional
    public ContaDTO updateConta (Long id, ContaPutDTO dto, String userId) {
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);
        Conta conta = contaRepository.findById(id).orElse(null);

        if (tipo == null) throw new IdTipoContaNaoEncontradoException();
        if (banco == null) throw new IdBancoNaoEncontradoException();
        if (conta == null) throw new IdContaNaoEncontradaException();
        if (!conta.getUsuario().getId().equals(userId)) throw new AcessoContaNegadoException();
        conta.setSaldo(dto.getSaldo());
        conta.setTipo_conta(tipo);
        conta.setBanco(banco);
        return new ContaDTO(contaRepository.save(conta));
    }

    @Transactional(readOnly = true)
    public SaldoTotalDTO getSaldo(String idUsuario) {
        BigDecimal saldo = new BigDecimal(0);
        List<Conta> contas = contaRepository.findAllByUsuario(idUsuario);
        for (Conta c : contas){
            saldo = saldo.add(c.getSaldo());
        }
        return new SaldoTotalDTO(saldo);
    }
}
