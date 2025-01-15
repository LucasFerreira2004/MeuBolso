package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
    @Autowired
    @Qualifier("transacaoService")
    private ITransacaoService transacaoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as transacoes realizadas pelo usuario")
    @GetMapping
    public List<TransacaoDTO> findTransacoes(){
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.findAll(userLogadoId);
    }

    @Operation(summary = "Retorna uma transacao expecifica a partir de um id indicado")
    @GetMapping ("/{id}")
    public TransacaoDTO findById(@PathVariable Long id){
        String userLogadoId = usuarioService.getUsuarioLogadoId();

        return transacaoService.findById(userLogadoId, id);
    }

    @Operation(summary = "Permite cadastrar uma transacao")
    @PostMapping
    public TransacaoDTO save(@Valid @RequestBody TransacaoSaveDTO dto){
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.save(userLogadoId, dto);
    }

}

