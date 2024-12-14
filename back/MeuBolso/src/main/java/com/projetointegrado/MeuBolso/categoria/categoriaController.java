package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaPostDTO;
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
    public List<CategoriaDTO> findReceitas() {
        return categoriaService.findAllByReceita();
    }
    @GetMapping("/despesas")
    public List<CategoriaDTO> findDespesas() {
        return categoriaService.findAllByDespesa();
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaPostDTO categoriaPostDTO) {
        return categoriaService.save(categoriaPostDTO);
    }
}
