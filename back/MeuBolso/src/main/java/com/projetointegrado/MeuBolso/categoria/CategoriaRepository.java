package com.projetointegrado.MeuBolso.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(nativeQuery = true, value = """
        select * from categoria 
        where usuario_id = :usuario_id 
        and interna_sistema = false
        order by nome asc;
    """)
    List<Categoria> findAllByUsuario(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria 
        where ativa = true 
        and usuario_id = :usuario_id 
        and interna_sistema = false
        order by nome asc;
    """)
    List<Categoria> findAllAtivas(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria 
        where ativa = false 
        and usuario_id = :usuarioId 
        and interna_sistema = false
        order by nome asc;
    """)
    List<Categoria> findAllArquivadas(String usuarioId);

    @Query(nativeQuery = true, value = """
        select * from categoria 
        where tipo_categoria = 'receita' 
        and ativa = true 
        and usuario_id = :usuario_id 
        and interna_sistema = false;
        order by nome asc;
    """)
    List<Categoria> findAllByReceita(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria 
        where tipo_categoria = 'despesa' 
        and ativa = true 
        and usuario_id = :usuario_id 
        and interna_sistema = false;
        order by nome asc;
    """)
    List<Categoria> findAllByDespesa(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria 
        where tipo_categoria = :tipo 
        and usuario_id = :usuario_id 
        and interna_sistema = false;
        order by nome asc;
    """)
    List<Categoria> findAllNotInternByTipo(String usuario_id, String tipo);

    @Query(nativeQuery = true, value = """
        select * from categoria 
        where LOWER(nome) like LOWER(:nome) 
        and usuario_id = :usuario_id;
    """)
    Categoria findByName(String usuario_id, String nome);

}
