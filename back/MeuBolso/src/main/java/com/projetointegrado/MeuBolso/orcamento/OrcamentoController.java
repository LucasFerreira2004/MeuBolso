package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    @Autowired
    @Qualifier("orcamentoService")
    private IOrcamentoService orcamentoService;
    
    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @GetMapping
    public List<OrcamentoDTO> findAll() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findAll(usuarioId);
    }

    @GetMapping("/{orcamentoId}")
    public OrcamentoDTO findById(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findById(orcamentoId, usuarioId);
    }

    @PostMapping
    public OrcamentoDTO save(@RequestBody OrcamentoPostDTO orcamento) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.save(usuarioId, orcamento);
    }

    @PutMapping("/{orcamentoId}")
    public OrcamentoDTO update(@PathVariable Long orcamentoId, @RequestBody OrcamentoPostDTO orcamento) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.update(orcamentoId, orcamento, usuarioId);
    }

    @DeleteMapping("/{orcamentoId}")
    public void delete(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        orcamentoService.deleteById(usuarioId, orcamentoId);
    }
}
