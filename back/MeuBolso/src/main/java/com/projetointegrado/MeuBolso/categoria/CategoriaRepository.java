package com.projetointegrado.MeuBolso.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(nativeQuery = true, value = """
        select * from categoria where tipo_categoria = 'receita'
    """)
    List<Categoria> findAllByReceita();
    @Query(nativeQuery = true, value = """
        select * from categoria where tipo_categoria = 'despesa'
    """)
    List<Categoria> findAllByDespesa();
    @Query(nativeQuery = true, value = """
        select * from categoria where LOWER(nome) like :nome
    """)
    Categoria findByName(String nome);
}
