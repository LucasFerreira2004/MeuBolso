package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.categoria.dto.ArquivarCategoriaPatchDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
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

import java.time.LocalDate;
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

    @Operation(summary = "Retorna todas as transações recorrentes arquivadas, fixas ou parceladas")
    @GetMapping("/arquivadas")
    public List<TransacaoRecorrenteDTO> findAllArquivadas(){
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoRecorrenteService.findAllArquivadas(userId);
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

    @Operation(summary = "Deleta uma transacao recorrente, fixa ou parcelada, deletando todas as transações associadas a ela")
    @DeleteMapping("/{id}")
    public TransacaoRecorrenteDTO delete (@PathVariable Long id){
        String userId = usuarioService.getUsuarioLogadoId();

        return transacaoRecorrenteService.delete(userId, id);
    }

    @Operation(summary = "desativa uma transação fixa e recebe uma data. Apaga todas as transações relacionadas a essa transação fixa depois da data recebida")
    @DeleteMapping("/futuras/{id}")
    public TransacaoRecorrenteDTO deleteFuturas(@PathVariable Long id, @RequestParam LocalDate data){
        String userId = usuarioService.getUsuarioLogadoId();
        return transacaoRecorrenteService.deleteAllAfterDate(userId, id, data);
    }

    @Operation(summary = "Atualiza o status de ativo de uma transacao recorrente, fixa ou parcelada (permitindo assim o 'soft delete'). Permite ativar e reativar uma transação fixa")
    @PatchMapping("/arquivadas/{id}")
    public TransacaoRecorrenteDTO atualizarStatusAtiva(@PathVariable Long id, @RequestBody ArquivarCategoriaPatchDTO dto, BindingResult bindingResult) throws ValoresNaoPermitidosException {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        return transacaoRecorrenteService.atualizarStatusAtiva(usuarioService.getUsuarioLogadoId(), id, dto.ativa());
    }
}