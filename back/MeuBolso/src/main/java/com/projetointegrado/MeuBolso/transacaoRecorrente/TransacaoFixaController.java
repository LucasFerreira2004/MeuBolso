package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoFixaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/transacoesFixas")
public class TransacaoFixaController {
    @Autowired
    private ITransacaoFixaService transacaoFixaService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public List<TransacaoFixaDTO> findAll(){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.findAll(userId);
    }

    @GetMapping("/{id}")
    public TransacaoFixaDTO findById(@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.findById(userId, id);
    }

    @PostMapping()
    public TransacaoFixaDTO save (@Valid @RequestBody TransacaoFixaSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.save(userId, dto);
    }

    @PutMapping("/{id}")
    public TransacaoFixaDTO update (@PathVariable Long id, @Valid @RequestBody TransacaoFixaSaveDTO dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.update(userId, id, dto);
    }

    @DeleteMapping("/{id}")
    public TransacaoFixaDTO delete (@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoFixaService.delete(userId, id);
    }
}