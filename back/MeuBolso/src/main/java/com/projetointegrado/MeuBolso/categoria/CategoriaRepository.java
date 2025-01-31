package com.projetointegrado.MeuBolso.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(nativeQuery = true, value = """
        select * from categoria where usuario_id = :usuario_id and interna_sistema = false;
    """)
    List<Categoria> findAllByUsuario(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria where ativa = true AND usuario_id = :usuario_id and interna_sistema = false;
    """)
    List<Categoria> findAllAtivas(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria where ativa = false and usuario_id = :usuarioId and interna_sistema = false;
    """)
    List<Categoria> findAllArquivadas(String usuarioId);

    @Query(nativeQuery = true, value = """
        select * from categoria where tipo_categoria = 'receita' AND ativa = true AND usuario_id = :usuario_id and interna_sistema = false;
    """)
    List<Categoria> findAllByReceita(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria where tipo_categoria = 'despesa' and ativa = true AND usuario_id = :usuario_id and interna_sistema = false;
    """)
    List<Categoria> findAllByDespesa(String usuario_id);

    @Query(nativeQuery = true, value = """
        select * from categoria where LOWER(nome) like LOWER(:nome) AND usuario_id = :usuario_id;
    """)
    Categoria findByName(String usuario_id, String nome);

}
