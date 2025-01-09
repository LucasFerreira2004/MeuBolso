package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
    @Autowired
    @Qualifier("contaService")
    private ContaService contaService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public List<ContaDTO> findAll() {
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAll(idUsuario);
    }

    @GetMapping("/{id}")
    public ContaDTO findById(@PathVariable Long id){
        return contaService.findById(id);
    }

    @GetMapping("/min")
    public List<ContaMinDTO> findMin() {
        String idUsuario = usuarioService.getUsuarioLogadoId();

        return contaService.findAllMin(idUsuario);
    }

    @GetMapping("/saldoTotal")
    public SaldoTotalDTO findSaldoTotal() {
        String idUsuario = usuarioService.getUsuarioLogadoId();
        return contaService.getSaldo(idUsuario);
    }

    @PostMapping
    public ContaDTO save(@RequestBody ContaPostDTO contaPostDTO){
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.saveConta(userId, contaPostDTO);
    }
    @PutMapping("/{id}")
    public ContaDTO update(@PathVariable Long id, @RequestBody ContaPutDTO contaPostDTO){
        String userId = usuarioService.getUsuarioLogadoId();
        return contaService.updateConta(id, contaPostDTO, userId);
    }
    @DeleteMapping("/{id}")
    public ContaDTO delete(@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return contaService.deleteConta(id, userId);
    }

}
