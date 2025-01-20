package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrcamentoService implements IOrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<Orcamento> findAll() {
        return orcamentoRepository.findAll();
    }

    @Transactional
    public Orcamento findById(Long id) {
        return orcamentoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Orcamento save(Orcamento orcamento) {
        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public Orcamento update(Long id, Orcamento orcamento) {
        Orcamento orcamentoAtual = orcamentoRepository.findById(id).orElse(null);
        if (orcamentoAtual == null) {
            return null;
        }
        orcamento.setId(orcamentoAtual.getId());

        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public void deleteById(Long id) {
        orcamentoRepository.deleteById(id);
    }
}
