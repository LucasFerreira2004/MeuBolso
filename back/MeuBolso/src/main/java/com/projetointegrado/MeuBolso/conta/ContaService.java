package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

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

    public Conta saveConta(Conta conta) {
        return contaRepository.save(conta);
    }
}
