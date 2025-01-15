package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.usuario.UsuarioService;
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
    public List<Meta> findAll() {
        return metaService.findAll(usuarioService.getUsuarioLogadoId());
    }

    @GetMapping("/{id}")
    public Meta findById(@PathVariable Long id) {
        return metaService.findById(usuarioService.getUsuarioLogadoId(), id).get();
    }

    @PostMapping
    public Meta save(@RequestBody Meta meta) {
        return metaService.save(meta);
    }

    @PutMapping
    public Meta update(@RequestBody Meta meta) {
        return metaService.update(meta);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        metaService.delete(usuarioService.getUsuarioLogadoId(), usuarioService.getUsuarioLogadoId());
    }
}
