package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(value = "/transacoesFixas")
public class TransacaoFixaController {
    @Autowired
    private ITransacaoFixaService transacaoFixaService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public List<TransacaoFixaDTO> findAll(){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.findAll(userId);
    }

    @GetMapping("/{id}")
    public TransacaoFixaDTO findById(@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.findById(userId, id);
    }

}