package com.projetointegrado.MeuBolso.orcamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    @Query("SELECT o FROM Orcamento o WHERE o.usuario.id = :idUsuario")
    List<Orcamento> findAllByUsuario(String idUsuario);

    @Query("SELECT o FROM Orcamento o WHERE o.categoria.id = :idCategoria AND o.usuario.id = :idUsuario")
    Optional<Orcamento> findOrcamentoByCategoriaAndUsuario(@Param("idCategoria") Long idCategoria, @Param("idUsuario") String idUsuario);

}
