package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
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


    @GetMapping
    public List<ContaDTO> findAll(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAll(idUsuario, data);
    }

    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id, @RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findById(idUsuario, id, data);
    }

    @GetMapping("/min")
    public List<ContaMinDTO> findMin(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAllMin(idUsuario, data);
    }

    @GetMapping("/saldoTotal")
    public SaldoTotalDTO findSaldoTotal(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        String idUsuario = usuarioService.getUsuarioLogadoId();
        return contaService.findSaldo(idUsuario, data);

    }

    @PostMapping
    public ContaDTO save(@RequestBody @Valid ContaPostDTO contaPostDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.save(userId, contaPostDTO);
    }

    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable Long id, @RequestBody @Valid ContaPutDTO contaPostDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.update(id, contaPostDTO, userId);
    }

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
