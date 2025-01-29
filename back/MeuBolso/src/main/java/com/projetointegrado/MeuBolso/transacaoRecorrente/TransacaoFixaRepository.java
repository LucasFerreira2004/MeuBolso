package com.projetointegrado.MeuBolso.transacaoRecorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoFixaRepository extends JpaRepository<TransacaoRecorrente, Long> {
    @Query(nativeQuery = true, value = """
        select * from transacao_fixa where usuario_id = :userId;
    """)
    public List<TransacaoRecorrente> findAllByUsuario(String userId);
}