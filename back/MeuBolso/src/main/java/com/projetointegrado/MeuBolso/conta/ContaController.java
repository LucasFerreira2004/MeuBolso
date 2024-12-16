package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.ContaMinDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaPostDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaPutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ContaDTO save(@RequestBody ContaPostDTO contaPostDTO){
        return contaService.saveConta(contaPostDTO);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ContaPutDTO contaPostDTO){
        contaService.updateConta(id, contaPostDTO);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        contaService.deleteConta(id);
    }

}
