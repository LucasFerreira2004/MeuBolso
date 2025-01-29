package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<ContaDTO> findAll(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAll(idUsuario, data);
    }

    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id, @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findById(idUsuario, id, data);
    }

    @GetMapping("/min")
    public List<ContaMinDTO> findMin(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAllMin(idUsuario, data);
    }

    @GetMapping("/saldoTotal")
    public SaldoTotalDTO findSaldoTotal(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        String idUsuario = usuarioService.getUsuarioLogadoId();
        return contaService.findSaldo(idUsuario, data);

    }

    @PostMapping
    public ContaDTO save(@RequestBody ContaPostDTO contaPostDTO){ //adicionar @Valid depois.
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.save(userId, contaPostDTO);
    }
    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable Long id, @RequestBody ContaPutDTO contaPostDTO){ //aterar para criar transacao de correcao de valor
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.update(id, contaPostDTO, userId);
    }
    @DeleteMapping("/{id}")
    public ContaDTO delete(@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return contaService.delete(id, userId);
    }

}
