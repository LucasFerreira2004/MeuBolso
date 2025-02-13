package com.projetointegrado.MeuBolso.banco;

import com.projetointegrado.MeuBolso.banco.dto.BancoDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/bancos")
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @Operation(summary = "Retorna todos os bancos registrados pelo usuario")
    @GetMapping
    public List<BancoDTO> findAll(){
        return bancoService.findAll();
    }

    @Operation(summary = "Retorna um banco especifico a partir de um id indicado")
    @GetMapping("/{id}")
    public BancoDTO findById(@PathVariable Long id) {
        return bancoService.findById(id);
    }
}
