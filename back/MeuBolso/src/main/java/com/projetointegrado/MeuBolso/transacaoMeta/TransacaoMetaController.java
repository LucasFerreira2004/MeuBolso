package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes-meta")
public class TransacaoMetaController {

    @Autowired
    private TransacaoMetaService transacaoMetaService;

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/{idMeta}")
    public TransacaoDTO criarTransacaoMeta(@RequestBody TransacaoMetaSaveDTO dto, @PathVariable Long idMeta) {

        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoMetaService.criarTransacaoMeta(userId, dto, idMeta);
//        return TransacaoMeta transacaoMeta = transacaoMetaService.criarTransacaoMeta(userId, dto.getValor(), dto.getData(), dto.getContaId(), idMeta);
//        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoMeta);
    }
}
