package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    public ContaDTO findById(Long id) {
        Conta result = contaRepository.findById(id).orElse(null);
        return new ContaDTO(result);
    }
    public List<ContaDTO> findAll(){
        List<Conta> result = contaRepository.findAll();
        return result.stream().map(ContaDTO::new).toList();
    }
}
