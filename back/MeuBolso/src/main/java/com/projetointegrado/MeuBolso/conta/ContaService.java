package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.banco.BancoRepository;
import com.projetointegrado.MeuBolso.banco.BancoService;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaPostDTO;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaRepository;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaService;
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

    @Transactional(readOnly = true)
    public ContaDTO findById(Long id) {
        Conta result = contaRepository.findById(id).orElse(null);
        return new ContaDTO(result);
    }
    @Transactional(readOnly = true)
    public List<ContaDTO> findAll(){
        List<Conta> result = contaRepository.findAll();
        return result.stream().map(ContaDTO::new).toList();
    }

    public Conta saveConta(ContaPostDTO dto) {
        //tratar erros de ids que não existem!.
        TipoConta tipo = tipoContaRepository.findById(dto.getId_tipo_conta()).orElse(null);
        Banco banco = bancoRepository.findById(dto.getId_banco()).orElse(null);
        Conta conta = new Conta(null, dto.getSaldo(), tipo, banco);
        return contaRepository.save(conta);
    }
}
