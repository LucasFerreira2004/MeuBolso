package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.banco.BancoRepository;
import com.projetointegrado.MeuBolso.categoria.exceptions.TipoCategoriaNaoEspecificado;
import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.conta.exception.IdBancoNaoEncontradoException;
import com.projetointegrado.MeuBolso.conta.exception.IdTipoContaNaoEncontradoException;
import com.projetointegrado.MeuBolso.conta.exception.IdUsuarioNaoEncontradoException;
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
        return new ContaDTO(result);
    }
    @Transactional(readOnly = true)
    public List<ContaDTO> findAll() {
        String idUsuario = usuarioService.getUsuarioLogadoId();

        List<Conta> result = contaRepository.findAllByUsuario(idUsuario);
        return result.stream().map(ContaDTO::new).toList();
    }
    @Transactional(readOnly = true)
    public List<ContaMinDTO> findAllMin() {
        List<Conta> result = contaRepository.findAll();
        return result.stream().map(ContaMinDTO::new).toList();
    }
    @Transactional
    public ContaDTO saveConta(String userID, ContaPostDTO dto) {
        //tratar erros de ids que não existem!.
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
    public ContaDTO deleteConta(Long id) {
        //tratar erro de id null
        Conta conta = contaRepository.findById(id).orElse(null);
        contaRepository.delete(conta);
        return new ContaDTO(conta);
    }

    @Transactional
    public ContaDTO updateConta (Long id, ContaPutDTO dto) {
        //tratar caso seja nulo!
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);

        Conta conta = contaRepository.findById(id).orElse(null);
        conta.setSaldo(dto.getSaldo());
        conta.setTipo_conta(tipo);
        conta.setBanco(banco);
        return new ContaDTO(contaRepository.save(conta));
    }

    @Transactional(readOnly = true)
    public SaldoTotalDTO getSaldo(){
        BigDecimal saldo = new BigDecimal(0);
        List<Conta> contas = contaRepository.findAll();
        for (Conta c : contas){
            saldo = saldo.add(c.getSaldo());
        }
        return new SaldoTotalDTO(saldo);
    }
}
