package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes-meta")
public class TransacaoMetaController {

    @Autowired
    private TransacaoMetaService transacaoMetaService;

    @Autowired
    private IUsuarioService usuarioService;


    @GetMapping
    public List<TransacaoMetaDTO> findAll() {
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.findAll(userId);
    }

    @GetMapping("/{idTransacao}")
    public TransacaoMetaDTO findById(@PathVariable Long idTransacao) {
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.findById(userId, idTransacao);
    }

    @PutMapping("/{idTransacao}")
    public TransacaoDTO update(@PathVariable Long idTransacao, @RequestBody TransacaoMetaSaveDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.update(userId, idTransacao, dto);
    }

    @PostMapping()
    public TransacaoDTO save(@RequestBody TransacaoMetaSaveDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.save(userId, dto);
    }

    @DeleteMapping("/{idTransacao}")
    public TransacaoMetaDTO delete(@PathVariable Long idTransacao) {
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.delete(userId, idTransacao);
    }
}
