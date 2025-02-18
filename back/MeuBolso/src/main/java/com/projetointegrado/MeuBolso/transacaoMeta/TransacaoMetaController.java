package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes-meta")
public class TransacaoMetaController {

    @Autowired
    private ITransacaoMetaService transacaoMetaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as transacoes de meta")
    @GetMapping
    public List<TransacaoMetaDTO> findAll() {
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.findAll(userId);
    }

    @Operation(summary = "Retorna uma transacao de meta especifico a partir de um id indicado")
    @GetMapping("/{idTransacao}")
    public TransacaoMetaDTO findById(@PathVariable Long idTransacao) {
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.findById(userId, idTransacao);
    }

    @Operation(summary = "Atualiza dados da transacao de meta")
    @PutMapping("/{idTransacao}")
    public TransacaoMetaDTO update(@PathVariable Long idTransacao, @RequestBody TransacaoMetaSaveDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.update(userId, idTransacao, dto);
    }

    @Operation(summary = "Cria uma nova transacao de meta")
    @PostMapping()
    public TransacaoMetaDTO save(@RequestBody TransacaoMetaSaveDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.save(userId, dto);
    }

    @Operation(summary = "Deleta uma transacao de meta")
    @DeleteMapping("/{idTransacao}")
    public TransacaoMetaDTO delete(@PathVariable Long idTransacao) {
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.delete(userId, idTransacao);
    }
}
