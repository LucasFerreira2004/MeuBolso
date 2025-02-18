package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.RouterOperation;
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

    @Operation(summary = "Retorna todos os orcamentos registrados pelo usuario")
    @GetMapping("/all")
    public List<OrcamentoDTO> findAll() {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findAll(usuarioId);
    }

    @Operation(summary = "Retorna todos os orcamentos registrados para o periodo selecionado")
    @GetMapping
    public List<OrcamentoDTO> findOrcamentosByPeriodo(
            @RequestParam("ano") Integer ano,
            @RequestParam("mes") Integer mes
    ) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findOrcamentosByPeriodo(usuarioId, ano, mes);
    }

    @Operation(summary = "Retorna um orcamento especifico a partir de um id indicado")
    @GetMapping("/{orcamentoId}")
    public OrcamentoDTO findById(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.findById(orcamentoId, usuarioId);
    }

    @Operation(summary = "Cria um novo orcamento")
    @PostMapping
    public OrcamentoDTO save(@Valid @RequestBody OrcamentoPostDTO orcamentoDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.save(orcamentoDTO, usuarioId);
    }

    @Operation(summary = "Atualiza um orcamento")
    @PutMapping("/{orcamentoId}")
    public OrcamentoDTO update(@Valid @RequestBody OrcamentoPostDTO orcamentoDTO, BindingResult bindingResult, @PathVariable Long orcamentoId) {
        if(bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.update(orcamentoId, orcamentoDTO, usuarioId);
    }

    @Operation(summary = "Deleta um orcamento")
    @DeleteMapping("/{orcamentoId}")
    public OrcamentoDTO delete(@PathVariable Long orcamentoId) {
        String usuarioId = usuarioService.getUsuarioLogadoId();
        return orcamentoService.deleteById(usuarioId, orcamentoId);
    }
}
