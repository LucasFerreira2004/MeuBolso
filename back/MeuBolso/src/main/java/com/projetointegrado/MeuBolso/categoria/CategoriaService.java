package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findCategoria() {
        List<Categoria> result = categoriaRepository.findAll();
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .map(CategoriaDTO::new)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    @Transactional
    public List<CategoriaDTO> findAllByReceita(){
        List<Categoria> result = categoriaRepository.findAllByReceita();
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional
    public List<CategoriaDTO> findAllByDespesa(){
        List<Categoria> result = categoriaRepository.findAllByDespesa();
        return result.stream().map(CategoriaDTO::new).toList();
    }

    @Transactional
    public CategoriaDTO save(CategoriaPostDTO dto) {
        if (categoriaRepository.findByName(dto.getNome()) != null) throw new RuntimeException("nome já cadastrado");
        Categoria categoria = new Categoria(null, dto.getNome(), dto.getTipo(), dto.getCor());
        return new CategoriaDTO(categoriaRepository.save(categoria));
    }
}
