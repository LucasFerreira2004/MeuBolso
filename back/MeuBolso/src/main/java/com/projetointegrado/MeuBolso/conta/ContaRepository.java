package com.projetointegrado.MeuBolso.conta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public  interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query(nativeQuery = true, value = """
        select * from conta where usuario_id = :idUsuario;
    """)
    public List<Conta> findAllByUsuario(String idUsuario);

    @Query(nativeQuery = true, value = """
        select * from conta where usuario_id = :userId and descricao = :descricao;
    """)
    public Conta findByDescricao(String userId, String descricao);

    @Query(nativeQuery = true, value = """
        select c.saldo_inicial + coalesce(sum(t.valor), 0) 
                - (
                    select coalesce(sum(t.valor), 0) 
                    from transacao t
                    where t.usuario_id = :userId
                    and t.data <= :dataFim
                    and t.tipo = 'DESPESA'
                    )
        from transacao t
        join conta c on c.id = t.conta_origem
        where t.usuario_id = :userId
        and t.data <= :dataFim
        and t.tipo = 'RECEITA'
        group by c.saldo_inicial;
    """)
    public BigDecimal getSaldoUntilDate(String userId, LocalDate dataFim);
}
