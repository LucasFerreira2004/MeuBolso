package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.ArquivarCategoriaPatchDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    @Qualifier("categoriaService")
    private ICategoriaService categoriaService;

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna todas as categorias ativas")
    @GetMapping
    public List<CategoriaDTO> findAllAtivas() {
        List<CategoriaDTO> result = categoriaService.findAllAtivas(usuarioService.getUsuarioLogadoId());
        return result;
    }

    @Operation(summary = "Retorna uma categoria pelo id")
    @GetMapping("/{id}")
    public CategoriaDTO findCategoriaById(@PathVariable Long id) {
        CategoriaDTO result = categoriaService.findById(usuarioService.getUsuarioLogadoId(), id);
        return result;
    }

    @Operation(summary = "Retorna todas as categorias de receitas")
    @GetMapping("/receitas")
    public List<CategoriaDTO> findReceitas() {
        return categoriaService.findAllByReceita(usuarioService.getUsuarioLogadoId());
    }

    @Operation(summary = "Retorna todas as categorias de despesas")
    @GetMapping("/despesas")
    public List<CategoriaDTO> findDespesas() {
        return categoriaService.findAllByDespesa(usuarioService.getUsuarioLogadoId());
    }

    @Operation(summary = "Cria uma nova categoria")
    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.save(usuarioService.getUsuarioLogadoId(), categoriaSaveDTO);
    }

    @Operation(summary = "Atualiza uma categoria")
    @PutMapping("{id}")
    public CategoriaDTO update(@PathVariable Long id, @RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.update(usuarioService.getUsuarioLogadoId(), id, categoriaSaveDTO);
    }

    //Rotas referentes específicas das arquivadas
    @Operation(summary = "Retorna todas as categorias arquivadas")
    @GetMapping("/arquivadas")
    public List<CategoriaDTO> findAllArquivadas() {
        List<CategoriaDTO> result = categoriaService.findAllArquivadas(usuarioService.getUsuarioLogadoId());
        return result;
    }

    @Operation(summary = "Atualiza o status de uma categoria arquivada")
    @PatchMapping("/arquivadas/{id}")
    public CategoriaDTO atualizarStatusAtiva(@PathVariable Long id, @RequestBody ArquivarCategoriaPatchDTO dto, BindingResult bindingResult) throws ValoresNaoPermitidosException {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        return categoriaService.atualizarStatusAtiva(usuarioService.getUsuarioLogadoId(), id, dto.ativa());
    }
}
