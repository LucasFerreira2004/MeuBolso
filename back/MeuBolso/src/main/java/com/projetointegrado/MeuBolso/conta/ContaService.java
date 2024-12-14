package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.banco.BancoRepository;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaMinDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaPostDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaPutDTO;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaRepository;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaService;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public ContaDTO findById(Long id) {
        Conta result = contaRepository.findById(id).orElse(null);
        return new ContaDTO(result);
    }
    @Transactional(readOnly = true)
    public List<ContaDTO> findAll() {
        List<Conta> result = contaRepository.findAll();
        return result.stream().map(ContaDTO::new).toList();
    }
    @Transactional(readOnly = true)
    public List<ContaMinDTO> findAllMin() {
        List<Conta> result = contaRepository.findAll();
        return result.stream().map(ContaMinDTO::new).toList();
    }
    @Transactional
    public Conta saveConta(ContaPostDTO dto) {
        //tratar erros de ids que não existem!.
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);
        Usuario usuario = usuarioRepository.findById(dto.getId_usuario()).orElse(null);

        Conta conta = new Conta(null, dto.getSaldo(), tipo, banco, usuario);
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta deleteConta(Long id) {
        //tratar erro de id null
        Conta conta = contaRepository.findById(id).orElse(null);
        contaRepository.delete(conta);
        return conta;
    }

    @Transactional
    public Conta updateConta (Long id, ContaPutDTO dto) {
        //tratar caso seja nulo!
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);

        Conta conta = contaRepository.findById(id).orElse(null);
        conta.setSaldo(dto.getSaldo());
        conta.setTipo_conta(tipo);
        conta.setBanco(banco);
        return contaRepository.save(conta);
    }
}
