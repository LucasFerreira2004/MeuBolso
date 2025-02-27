package com.projetointegrado.MeuBolso.orcamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    @Query("SELECT o FROM Orcamento o WHERE o.usuario.id = :idUsuario")
    List<Orcamento> findAllByUsuario(String idUsuario);

    // Busca um orçamento específico para um usuário, em um determinado período e categoria.
    @Query(value = """
    SELECT o.* 
    FROM orcamento o
    JOIN categoria c ON o.categoria_id = c.id
    WHERE o.usuario_id = :usuarioId 
      AND o.ano = :ano 
      AND o.mes = :mes 
      AND c.id = :categoriaId
    """, nativeQuery = true)
    Optional<Orcamento> findByCategoriaUsuarioAndPeriodo(@Param("categoriaId") Long categoriaId,
                                                         @Param("usuarioId") String usuarioId,
                                                         @Param("ano") int ano,
                                                         @Param("mes") int mes);

    // Busca todos os orçamentos de um usuário para um determinado período.
    @Query(value = """
    SELECT o.* 
    FROM orcamento o
    WHERE o.usuario_id = :usuarioId 
      AND o.ano = :ano 
      AND o.mes = :mes
    """, nativeQuery = true)
    List<Orcamento> findByUsuarioAndPeriodo(@Param("usuarioId") String usuarioId,
                                            @Param("ano") int ano,
                                            @Param("mes") int mes);

}
