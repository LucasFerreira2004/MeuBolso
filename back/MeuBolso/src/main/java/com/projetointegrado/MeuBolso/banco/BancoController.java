package com.projetointegrado.MeuBolso.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/bancos")
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @GetMapping
    public List<BancoDTO> findAll(){
        return bancoService.findAll();
    }

    public BancoDTO findById(Long id) {
        return bancoService.findById(id);
    }
}
