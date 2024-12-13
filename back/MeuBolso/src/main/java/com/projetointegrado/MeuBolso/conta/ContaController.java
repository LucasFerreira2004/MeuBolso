package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.ContaPostDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    @PostMapping
    public void save(@RequestBody ContaPostDTO contaPostDTO){
        Conta contaSalva = contaService.saveConta(contaPostDTO);
    }
}
