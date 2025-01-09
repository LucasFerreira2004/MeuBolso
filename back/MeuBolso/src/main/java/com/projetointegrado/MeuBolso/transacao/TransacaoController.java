package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
    @Autowired
    @Qualifier("transacaoService")
    private ITransacaoService transacaoService;

    @GetMapping
    public List<TransacaoDTO> findTransacoes(){
        return transacaoService.findAll();
    }

    @GetMapping ("/{id}")
    public TransacaoDTO findById(@PathVariable Long id){
        return transacaoService.findById(id);
    }

}

