package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Autowired
    @Qualifier("orcamentoService")
    private IOrcamentoService orcamentoService;
    
    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @GetMapping
    public List<Orcamento> findAll() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findAll();
    }

    @GetMapping("/{orcamentoId}")
    public Orcamento findById(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findById(orcamentoId);
    }

    @PostMapping
    public Orcamento save(@RequestBody Orcamento orcamento) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.save(orcamento);
    }

    @PutMapping("/{orcamentoId}")
    public Orcamento update(@PathVariable Long orcamentoId, @RequestBody Orcamento orcamento) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.update(orcamentoId, orcamento);
    }

    @DeleteMapping("/{orcamentoId}")
    public void delete(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        orcamentoService.deleteById(orcamentoId);
    }
}
