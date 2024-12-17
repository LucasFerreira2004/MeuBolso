package com.projetointegrado.MeuBolso.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(nativeQuery = true, value = """
        select * from categoria where ativa = true
    """)
    List<Categoria> findAllAtivas();

    @Query(nativeQuery = true, value = """
        select * from categoria where tipo_categoria = 'receita' AND ativa = true
    """)
    List<Categoria> findAllByReceita();

    @Query(nativeQuery = true, value = """
        select * from categoria where tipo_categoria = 'despesa' and ativa = true
    """)
    List<Categoria> findAllByDespesa();

    @Query(nativeQuery = true, value = """
        select * from categoria where LOWER(nome) like LOWER(:nome)
    """)
    Categoria findByName(String nome);

    @Modifying
    @Query(nativeQuery = true, value = """
        update categoria
        set ativa = false
        where id = :id and ativa = true;
    """)
    void arquivarById (Long id);
}
