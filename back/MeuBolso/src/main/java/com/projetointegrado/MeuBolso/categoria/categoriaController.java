package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class categoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> findCategoria() {
        List<CategoriaDTO> result = categoriaService.findCategoria();
        return result;
    }

    @GetMapping("/{id}")
    public CategoriaDTO findCategoriaById(@PathVariable Long id) {
        CategoriaDTO result = categoriaService.findCategoriaById(id);
        return result;
    }

    @GetMapping("/receitas")
    public List<CategoriaDTO> findReceitas(String usuarioId) {
        return categoriaService.findAllByReceita(usuarioId);
    }
    @GetMapping("/despesas")
    public List<CategoriaDTO> findDespesas(String usuarioId) {
        return categoriaService.findAllByDespesa(usuarioId);
    }

    @PostMapping
    public CategoriaDTO save(String usuarioId, @RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.save(usuarioId, categoriaSaveDTO);
    }
    @PutMapping("{id}")
    public CategoriaDTO update(@PathVariable Long id, @RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.update(id, categoriaSaveDTO);
    }

    @PutMapping("/arquivadas/{id}")
    public CategoriaDTO arquivar(@PathVariable Long id) {
        return categoriaService.arquivar(id);
    }
}
