package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
    @Autowired
    @Qualifier("contaService")
    private IContaService contaService;

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as contas registradas pelo usuario")
    @GetMapping
    public List<ContaDTO> findAll(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAll(idUsuario, data);
    }

    @Operation(summary = "Retorna uma conta pelo id")
    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id, @RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findById(idUsuario, id, data);
    }

    @Operation(summary = "Retorna todas as contas formatadas com minimas informacoes")
    @GetMapping("/min")
    public List<ContaMinDTO> findMin(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAllMin(idUsuario, data);
    }

    @Operation(summary = "Retorna o saldo total de todas as contas")
    @GetMapping("/saldoTotal")
    public SaldoTotalDTO findSaldoTotal(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();
        return contaService.findSaldo(idUsuario, data);

    }

    @Operation(summary = "Cria uma nova conta")
    @PostMapping
    public ContaDTO save(@RequestBody @Valid ContaPostDTO contaPostDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.save(userId, contaPostDTO);
    }

    @Operation(summary = "Atualiza uma conta")
    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable Long id, @RequestBody @Valid ContaPutDTO contaPostDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.update(id, contaPostDTO, userId);
    }

    @Operation(summary = "Deleta uma conta")
    @DeleteMapping("/{id}")
    public ContaDTO delete(@PathVariable Long id){
        //try {
            String userId = usuarioService.getUsuarioLogadoId();
            return contaService.delete(id, userId);
       // }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

}
