package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @Operation(summary = "Retorna todas as contas registradas")
    @GetMapping
    public List<ContaDTO> findAll() {
        return contaService.findAll();
    }

    @Operation(summary = "Retorna uma conta expecifica a partir de um id indicado")
    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id){
        return contaService.findById(id);
    }

    @Operation(summary = "Retorna todas as contas, mas no formato de ContaMinDTO")
    @GetMapping("/min")
    public List<ContaMinDTO> findMin() {
        return contaService.findAllMin();
    }

    @Operation(summary = "Retorna o valor total somado de todo o saldo do usuario")
    @GetMapping("/saldoTotal")
    public SaldoTotalDTO findSaldoTotal() {
        return contaService.getSaldo();
    }

    @Operation(summary = "Registra uma nova conta")
    @PostMapping
    public ContaDTO save(@RequestBody ContaPostDTO contaPostDTO){
        return contaService.saveConta(contaPostDTO);
    }

    @Operation(summary = "Atualiza dados de uma conta ja existente")
    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable Long id, @RequestBody ContaPutDTO contaPostDTO){
        return contaService.updateConta(id, contaPostDTO);
    }

    @Operation(summary = "Deleta uma conta existente")
    @DeleteMapping("/{id}")
    public ContaDTO delete(@PathVariable Long id){
        return contaService.deleteConta(id);
    }
}
