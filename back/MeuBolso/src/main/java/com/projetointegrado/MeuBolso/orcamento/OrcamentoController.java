package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/all")
    public List<OrcamentoDTO> findAll() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findAll(usuarioId);
    }

    @GetMapping
    public List<OrcamentoDTO> findOrcamentosByPeriodo(
            @RequestParam("ano") Integer ano,
            @RequestParam("mes") Integer mes
    ) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findOrcamentosByPeriodo(usuarioId, ano, mes);
    }

    @GetMapping("/{orcamentoId}")
    public OrcamentoDTO findById(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findById(orcamentoId, usuarioId);
    }

    @PostMapping
    public OrcamentoDTO save(@Valid @RequestBody OrcamentoPostDTO orcamentoDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.save(orcamentoDTO, usuarioId);
    }

    @PutMapping("/{orcamentoId}")
    public OrcamentoDTO update(@Valid @RequestBody OrcamentoPostDTO orcamentoDTO, BindingResult bindingResult, @PathVariable Long orcamentoId) {
        if(bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.update(orcamentoId, orcamentoDTO, usuarioId);
    }

    @DeleteMapping("/{orcamentoId}")
    public OrcamentoDTO delete(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.deleteById(usuarioId, orcamentoId);
    }
}
