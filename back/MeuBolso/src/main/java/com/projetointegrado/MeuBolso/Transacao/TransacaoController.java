package com.projetointegrado.MeuBolso.Transacao;

import com.projetointegrado.MeuBolso.Transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;

    @GetMapping ("/{id}")
    public TransacaoDTO findById(@PathVariable Long id){
        return transacaoService.findTransacaoById(id);
    }
}

