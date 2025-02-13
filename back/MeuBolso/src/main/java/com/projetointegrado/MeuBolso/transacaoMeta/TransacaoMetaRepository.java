package com.projetointegrado.MeuBolso.transacaoMeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoMetaRepository extends JpaRepository<TransacaoMeta, Long> {
    @Query(value = "SELECT tm.* FROM transacao_meta tm JOIN meta m ON tm.meta_id = m.id WHERE m.usuario_id = :usuarioId", nativeQuery = true)
    List<TransacaoMeta> findAllByUsuario(@Param("usuarioId") String usuarioId);

    @Query(value = "SELECT tm.* FROM transacao_meta tm JOIN meta m ON tm.meta_id = m.id WHERE m.id = :metaId", nativeQuery = true)
    List<TransacaoMeta> findAllByMeta(@Param("metaId") Long metaId);
}
