package com.projetointegrado.MeuBolso.transacaoMeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoMetaRepository extends JpaRepository<TransacaoMeta, Long> {
    @Query(nativeQuery = true, value = "select * from transacao_meta where usuario_id = :userId;")
    List<TransacaoMeta> findAllByUsuario(String userId);
}
