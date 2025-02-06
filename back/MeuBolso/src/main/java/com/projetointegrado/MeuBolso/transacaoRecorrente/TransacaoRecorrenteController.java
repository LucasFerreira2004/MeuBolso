package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoRecorrenteDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoFixaSaveDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoParceladaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/transacoesRecorrentes")
public class TransacaoRecorrenteController {
    @Autowired
    private ITransacaoRecorrenteService transacaoRecorrenteService;

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as transações recorrentes, fixas ou parceladas")
    @GetMapping
    public List<TransacaoRecorrenteDTO> findAll(){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.findAll(userId);
    }

    @Operation(summary = "Retorna uma transacao recorrente, fixa ou parcelada")
    @GetMapping("/{id}")
    public TransacaoRecorrenteDTO findById(@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.findById(userId, id);
    }
    @Operation(summary = "Salva uma transacao fixa")
    @PostMapping("/fixas")
    public TransacaoRecorrenteDTO save (@Valid @RequestBody TransacaoFixaSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.save(userId, dto);
    }

    @Operation(summary = "Salva uma transacao parcelada")
    @PostMapping("/parceladas")
    public TransacaoRecorrenteDTO save (@Valid @RequestBody TransacaoParceladaSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.save(userId, dto);
    }

    @Operation(summary = "Update em uma transacao fixa")
    @PutMapping("/fixas/{id}")
    public TransacaoRecorrenteDTO update (@PathVariable Long id, @Valid @RequestBody TransacaoFixaSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.update(userId, id, dto);
    }

    @Operation(summary = "Update em uma transacao parcelada")
    @PutMapping("/parceladas/{id}")
    public TransacaoRecorrenteDTO update (@PathVariable Long id, @Valid @RequestBody TransacaoParceladaSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.update(userId, id, dto);
    }

    @Operation(summary = "Deleta uma transacao recorrente, fixa ou parcelada")
    @DeleteMapping("/{id}")
    public TransacaoRecorrenteDTO delete (@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.delete(userId, id);
    }
}