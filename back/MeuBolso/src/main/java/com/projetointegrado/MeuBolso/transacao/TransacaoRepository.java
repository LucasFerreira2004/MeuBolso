package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.SumTransacoesDTO;
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
        select * from transacao 
        where usuario_id = :userId
        order by data desc, id desc;
    """)
    public List<Transacao> findAllByUsuario(String userId);


    @Query(nativeQuery = true, value = """
        select * from transacao 
        where usuario_id = :userId 
        and data between :dataInicio and :dataFim
        order by data desc, id desc;
    """)
    public List<Transacao> findAllInRange(LocalDate dataInicio, LocalDate dataFim, String userId);

    @Query(nativeQuery = true, value = """
        select * from transacao 
        where data <= :data 
        and usuario_id = :userId
        order by data desc, id desc;
    """)
    public List<Transacao> findAllBeforeDate(LocalDate data, String userId);

    @Query(nativeQuery = true, value = """
        select * from transacao 
        where usuario_id = :userId 
        and categoria_id = :categoria_id 
        and data between :dataInicial and :dataFinal
        order by data desc, id desc;
    """)
    public List<Transacao> findAllInRangeByCategoria(String userId, LocalDate dataInicial, LocalDate dataFinal, Long categoria_id);

    @Query(nativeQuery = true, value = """
        select sum(transacao.valor) 
        from transacao 
        where usuario_id = :userId and categoria_id = :categoria_id and data between :dataInicial and :dataFinal
    """)
    public BigDecimal getSumInRangeByCategoria(LocalDate dataInicial, LocalDate dataFinal, Long categoria_id, String userId);

    @Query(nativeQuery = true, value = """
        select coalesce(sum(valor), 0)
        from transacao as t,
             categoria as c
        where c.id = t.categoria_id
              and t.usuario_id = :userId  
              and t.tipo = :tipo 
              and t.data between :dataInicio and :dataFim
              and c.interna_sistema = false;
    """)
    public BigDecimal getSumInRangeByTipo(LocalDate dataInicio, LocalDate dataFim, String userId, String tipo);

    @Query(value = """
    select coalesce(sum(t.valor), 0) 
    from transacao t 
    where t.categoria_id = :categoriaId 
    and t.usuario_id = :usuarioId 
    and t.data between :dataInicio and :dataFim
    """, nativeQuery = true)
    public BigDecimal calcularGastoPorCategoriaEPeriodo(@Param("categoriaId") Long categoriaId,
                                                        @Param("usuarioId") String usuarioId,
                                                        @Param("dataInicio") LocalDate dataInicio,
                                                        @Param("dataFim") LocalDate dataFim);
}
