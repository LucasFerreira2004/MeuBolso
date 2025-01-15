package com.projetointegrado.MeuBolso.meta;

import java.util.List;
import java.util.Optional;

public interface IMetaService {
    public List<Meta> findAll(String usuarioId);
    public Optional<Meta> findById(String usuarioId, Long metaId);
    public Meta save(Meta meta);
    public Meta update(Meta meta);
    public void delete(String usuarioId, String metaId);
}
