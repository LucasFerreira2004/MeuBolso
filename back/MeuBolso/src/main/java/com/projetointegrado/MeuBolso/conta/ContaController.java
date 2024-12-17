package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
    @Autowired
    private ContaService contaService;
    @GetMapping
    public List<ContaDTO> findAll() {
        return contaService.findAll();
    }
    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id){
        return contaService.findById(id);
    }
    @GetMapping("/min")
    public List<ContaMinDTO> findMin() {
        return contaService.findAllMin();
    }
    @GetMapping("/saldoTotal")
    public SaldoTotalDTO findSaldoTotal() {
        return contaService.getSaldo();
    }
    @PostMapping
    public ContaDTO save(@RequestBody ContaPostDTO contaPostDTO){
        return contaService.saveConta(contaPostDTO);
    }
    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable Long id, @RequestBody ContaPutDTO contaPostDTO){
        return contaService.updateConta(id, contaPostDTO);
    }
    @DeleteMapping("/{id}")
    public ContaDTO delete(@PathVariable Long id){
        return contaService.deleteConta(id);
    }

}
