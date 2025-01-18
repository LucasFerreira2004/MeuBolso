package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/metas")
public class MetaController {

    @Autowired
    @Qualifier("metaService")
    private MetaService metaService;

    @Autowired
    @Qualifier("usuarioService")
    private UsuarioService usuarioService;

    @GetMapping
    public List<MetaDTO> findAll() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.findAll(usuarioId);
    }

    @GetMapping("/{metaId}")
    public MetaDTO findById(@PathVariable Long metaId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.findById(usuarioId, metaId);
    }

    @PostMapping
    public MetaDTO save(@RequestBody MetaPostDTO meta) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.save(usuarioId, meta);
    }

    @PutMapping("/{metaId}")
    public MetaDTO update(@PathVariable Long metaId, @RequestBody MetaPostDTO meta) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return metaService.update(usuarioId, metaId, meta);
    }

    @DeleteMapping("/{metaId}")
    public void delete(@PathVariable Long metaId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        metaService.delete(usuarioId, metaId);
    }
}
