package com.projetointegrado.MeuBolso.tipoConta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipoConta")
public class TipoController {
    @Autowired
    TipoContaService tipoContaService;

    public TipoController(TipoContaRepository tipoContaRepository) {
    }

    @GetMapping
    public List<TipoContaDTO> findAll(){
        return tipoContaService.findAll();
    }
    @GetMapping("{id}")
    public TipoContaDTO findById(@PathVariable Long id) {
        return tipoContaService.findById(id);
    }
}
