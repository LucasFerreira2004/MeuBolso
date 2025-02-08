package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes-meta")
public class TransacaoMetaController {

    @Autowired
    private TransacaoMetaService transacaoMetaService;

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/{idMeta}")
    public TransacaoDTO saveTransacao(@RequestBody TransacaoMetaSaveDTO dto, BindingResult bindingResult, @PathVariable Long idMeta) {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.save(userId, dto, idMeta);
    }
}
