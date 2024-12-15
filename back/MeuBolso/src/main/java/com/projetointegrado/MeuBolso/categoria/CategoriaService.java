package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findCategoria() {
        List<Categoria> result = categoriaRepository.findAllAtivas();
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
    public CategoriaDTO save(CategoriaSaveDTO dto) {
        //tratar exceção e null
        if (categoriaRepository.findByName(dto.getNome()) != null) throw new RuntimeException("nome já cadastrado");

        Categoria categoria = new Categoria(null, dto.getNome(), dto.getTipo(), dto.getCor(), true);
        return new CategoriaDTO(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaSaveDTO dto) {
        if (categoriaRepository.findById(id) == null) throw new RuntimeException("id não encontrado");
        Categoria categira = new Categoria(id, dto.getNome(), dto.getTipo(), dto.getCor(), true);
        categoriaRepository.save(categira);
        return new CategoriaDTO(categira);
    }

    @Transactional
    public void arquivar(Long id) {
        if (categoriaRepository.findById(id) == null) { throw new RuntimeException("id não encontrado");}
        categoriaRepository.arquivarById(id);
    }
}
