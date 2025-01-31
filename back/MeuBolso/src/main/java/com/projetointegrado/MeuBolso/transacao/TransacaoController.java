package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
    @Autowired
    private ITransacaoService transacaoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as transacoes do inicio do mês até a data especificada")
    @GetMapping
    public List<TransacaoDTO> findAll(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.findAllInRangeByMonth(userLogadoId, data);
    }

    @Operation(summary = "Retorna uma transacao expecifica a partir de um id indicado")
    @GetMapping ("/{id}")
    public TransacaoDTO findById(@PathVariable Long id){
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.findById(userLogadoId, id);
    }

    @Operation(summary = "Retorna o somatório das despesas do inicio do mês até a data especificada")
    @GetMapping("/somatorioDespesas")
    public BigDecimal findSumDespesasInRangeByMonth(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.findSumDespesasInRangeByMonth(userLogadoId, data);
    }

    @Operation(summary = "Retorna o somatório das receitas do inicio do mês até a data especificada")
    @GetMapping("/somatorioReceitas")
    public BigDecimal findSumReceitasInRangeByMonth(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.findSumReceitasInRangeByMonth(userLogadoId, data);
    }


    @Operation(summary = "Permite cadastrar uma transacao")
    @PostMapping
    public TransacaoDTO save(@Valid @RequestBody TransacaoSaveDTO dto, BindingResult bindingResult) throws ValoresNaoPermitidosException {
        if (bindingResult.hasErrors()){
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.save(userLogadoId, dto);
    }

    @Operation(summary = "Permite update em uma transação já existente")
    @PutMapping("/{id}")
    public TransacaoDTO update(@PathVariable Long id, @Valid @RequestBody TransacaoSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.update(userLogadoId, id, dto);
    }

    @Operation(summary = "Permite deletar uma transacao já existe")
    @DeleteMapping("/{id}")
    public TransacaoDTO delete(@PathVariable Long id){
        String userLogadoId = usuarioService.getUsuarioLogadoId();
        return transacaoService.delete(userLogadoId, id);
    }
}

