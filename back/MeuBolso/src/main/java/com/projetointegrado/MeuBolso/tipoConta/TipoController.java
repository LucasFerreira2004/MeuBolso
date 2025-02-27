package com.projetointegrado.MeuBolso.tipoConta;

import com.projetointegrado.MeuBolso.tipoConta.dto.TipoContaDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipoConta")
public class TipoController {
    @Autowired
    @Qualifier("tipoContaService")
    ITipoContaService tipoContaService;

    public TipoController(TipoContaRepository tipoContaRepository) {
    }

    @Operation(summary = "Retorna todos os tipos de contas cadastradas no sistema")
    @GetMapping
    public List<TipoContaDTO> findAll(){
        return tipoContaService.findAll();
    }

    @Operation(summary = "Retorna um tipo de conta pelo id")
    @GetMapping("/{id}")
    public TipoContaDTO findById(@PathVariable Long id) {
        return tipoContaService.findById(id);
    }
}
