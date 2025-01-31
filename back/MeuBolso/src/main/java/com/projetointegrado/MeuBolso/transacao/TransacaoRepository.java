package com.projetointegrado.MeuBolso.transacao;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query(nativeQuery = true, value = """
        select * from transacao 
        where usuario_id = :userId and categoria_id = :categoria_id and data between :dataInicio and :dataFim
    """)
    public List<Transacao> findAllInRangeByCategoria(LocalDate dataInicial, LocalDate dataFinal, Long categoria_id, String userId);

    @Query(nativeQuery = true, value = """
        select sum(transacao.valor) 
        from transacao 
        where usuario_id = :userId and categoria = :categoria_id and data between :dataInicial and :dataFinal
    """)
    public BigDecimal getSomatorioInRangeByCategoria(LocalDate dataInicial, LocalDate dataFinal, Long categoria_id, String userId);
}
