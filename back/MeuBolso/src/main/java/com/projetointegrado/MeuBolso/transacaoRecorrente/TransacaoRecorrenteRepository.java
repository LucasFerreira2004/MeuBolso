package com.projetointegrado.MeuBolso.transacaoRecorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRecorrenteRepository extends JpaRepository<TransacaoRecorrente, Long> {
    @Query(nativeQuery = true, value = """
        select * from TRANSACAO_RECORRENTE where usuario_id = :userId and ativa = true;
    """)
    public List<TransacaoRecorrente> findAllByUsuario(String userId);

    @Query(nativeQuery = true, value = """
        select * from TRANSACAO_RECORRENTE where usuario_id = :userId and ativa = false;
    """)
    public List<TransacaoRecorrente> findAllArquivadasByUsuario(String userId);
}