package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @Operation(summary = "Retorna todas as categorias registradas que estão ativas")
    @GetMapping
    public List<CategoriaDTO> findCategoria() {
        List<CategoriaDTO> result = categoriaService.findCategoria();
        return result;
    }

    @Operation(summary = "Retorna uma categoria expecifica a partir de um id indicado")
    @GetMapping("/{id}")
    public CategoriaDTO findCategoriaById(@PathVariable Long id) {
        CategoriaDTO result = categoriaService.findCategoriaById(id);
        return result;
    }

    @Operation(summary = "Retorna todas as categorias registradas do tipo 'receita'")
    @GetMapping("/receitas")
    public List<CategoriaDTO> findReceitas() {
        return categoriaService.findAllByReceita();
    }

    @Operation(summary = "Retorna todas as categorias registradas do tipo 'despesas'")
    @GetMapping("/despesas")
    public List<CategoriaDTO> findDespesas() {
        return categoriaService.findAllByDespesa();
    }

    @Operation(summary = "Registra uma nova categoria")
    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.save(categoriaSaveDTO);
    }

    @Operation(summary = "Atualiza os dados de uma categoria ja registrada")
    @PutMapping("{id}")
    public CategoriaDTO update(@PathVariable Long id, @RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.update(id, categoriaSaveDTO);
    }

    @Operation(summary = "Modifica uma categoria para 'arquivada', ou seja, sera deletada para o usuario")
    @PutMapping("/arquivadas/{id}")
    public CategoriaDTO arquivar(@PathVariable Long id) {
        return categoriaService.arquivar(id);
    }
}
