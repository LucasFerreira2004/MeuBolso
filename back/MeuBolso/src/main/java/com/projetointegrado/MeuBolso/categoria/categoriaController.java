package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class categoriaController {

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @GetMapping
    public List<CategoriaDTO> findCategoria() {
        List<CategoriaDTO> result = categoriaService.findCategoria(usuarioService.getUsuarioLogadoId());
        return result;
    }

    @GetMapping("/{id}")
    public CategoriaDTO findCategoriaById(@PathVariable Long id) {
        CategoriaDTO result = categoriaService.findCategoriaById(usuarioService.getUsuarioLogadoId(), id);
        return result;
    }

    @GetMapping("/receitas")
    public List<CategoriaDTO> findReceitas() {
        return categoriaService.findAllByReceita(usuarioService.getUsuarioLogadoId());
    }
    @GetMapping("/despesas")
    public List<CategoriaDTO> findDespesas() {
        return categoriaService.findAllByDespesa(usuarioService.getUsuarioLogadoId());
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.save(usuarioService.getUsuarioLogadoId(), categoriaSaveDTO);
    }
    @PutMapping("{id}")
    public CategoriaDTO update(@PathVariable Long id, @RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.update(usuarioService.getUsuarioLogadoId(), id, categoriaSaveDTO);
    }

    @PutMapping("/arquivadas/{id}")
    public CategoriaDTO arquivar(@PathVariable Long id) {
        return categoriaService.arquivar(usuarioService.getUsuarioLogadoId(), id);
    }
}
