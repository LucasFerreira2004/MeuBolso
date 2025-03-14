package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.meta.dto.MetaCardDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/metas")
public class MetaController {

    @Autowired
    @Qualifier("metaService")
    private IMetaService metaService;

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as metas cadastradas pelo usuario")
    @GetMapping
    public List<MetaDTO> findAll() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.findAll(usuarioId);
    }

    @Operation(summary = "Retorna todas as metas cadastradas com informacoes para os cards")
    @GetMapping("/cards")
    public List<MetaCardDTO> findAllCards() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.findAllCards(usuarioId);
    }

    @Operation(summary = "Retorna uma meta especifica a partir de um id indicado")
    @GetMapping("/{metaId}")
    public MetaDTO findById(@PathVariable Long metaId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.findById(usuarioId, metaId);
    }

    @Operation(summary = "Salva uma meta")
    @PostMapping
    public MetaDTO save(@Valid @RequestBody MetaPostDTO meta, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.save(usuarioId, meta);
    }

    @Operation(summary = "Atualiza uma meta")
    @PutMapping("/{metaId}")
    public MetaDTO update(@PathVariable Long metaId, @Valid @RequestBody MetaPostDTO meta, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.update(usuarioId, metaId, meta);
    }

    @Operation(summary = "Deleta uma meta")
    @DeleteMapping("/{metaId}")
    public void delete(@PathVariable Long metaId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        metaService.delete(usuarioId, metaId);
    }
}
