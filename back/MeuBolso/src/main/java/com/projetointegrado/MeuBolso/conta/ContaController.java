package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.Transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
    @Autowired
    private ContaService contaService;
    @GetMapping
    public List<ContaDTO> findAll(){
        return contaService.findAll();
    }
    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id){
        return contaService.findById(id);
    }
}
