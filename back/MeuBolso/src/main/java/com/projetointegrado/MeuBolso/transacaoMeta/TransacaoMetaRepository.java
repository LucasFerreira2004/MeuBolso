package com.projetointegrado.MeuBolso.transacaoMeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoMetaRepository extends JpaRepository<TransacaoMeta, Long> {
}
