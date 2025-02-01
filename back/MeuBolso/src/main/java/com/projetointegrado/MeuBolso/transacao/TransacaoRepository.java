package com.projetointegrado.MeuBolso.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query(nativeQuery = true, value = """
        select * from transacao where usuario_id = :userId;
    """)
    public List<Transacao> findAllByUsuario(String userId);


    @Query(nativeQuery = true, value = """
        select * from transacao 
        where usuario_id = :userId and data between :dataInicio and :dataFim;
    """)
    public List<Transacao> findAllInRange(LocalDate dataInicio, LocalDate dataFim, String userId);

    @Query(nativeQuery = true, value = """
        select * from transacao where data <= :data and usuario_id = :userId;
    """)
    public List<Transacao> findAllBeforeDate(LocalDate data, String userId);

    @Query(value = """
    SELECT COALESCE(SUM(t.valor), 0) 
    FROM transacao t 
    WHERE t.categoria_id = :categoriaId 
    AND t.usuario_id = :usuarioId 
    AND t.data BETWEEN :dataInicio AND :dataFim
    """, nativeQuery = true)
    public BigDecimal calcularGastoPorCategoriaEPeriodo(@Param("categoriaId") Long categoriaId,
                                                 @Param("usuarioId") String usuarioId,
                                                 @Param("dataInicio") LocalDate dataInicio,
                                                 @Param("dataFim") LocalDate dataFim);

}
