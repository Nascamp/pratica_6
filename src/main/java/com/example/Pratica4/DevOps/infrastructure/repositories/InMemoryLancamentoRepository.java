package com.example.Pratica4.DevOps.infrastructure.repositories;

import com.example.Pratica4.DevOps.domain.entities.Lancamento;
import com.example.Pratica4.DevOps.domain.ports.LancamentoRepository;
import java.util.*;

/**
 * Adapter: InMemoryLancamentoRepository
 * Implementação em memória do repositório de lançamentos.
 * Adequada para testes e prototipagem.
 */
public class InMemoryLancamentoRepository implements LancamentoRepository {
    private final Map<String, List<Lancamento>> store = new HashMap<>();

    @Override
    public void saveAll(String aulaId, List<Lancamento> itens) {
        if (aulaId == null || aulaId.isBlank()) {
            throw new IllegalArgumentException("Identificador da aula obrigatório");
        }
        if (itens == null) {
            throw new IllegalArgumentException("Lista de itens não pode ser nula");
        }
        
        store.computeIfAbsent(aulaId, k -> new ArrayList<>()).addAll(itens);
    }

    @Override
    public int countByAula(String aulaId) {
        return store.getOrDefault(aulaId, List.of()).size();
    }

    /**
     * Método auxiliar para testes: retorna todos os lançamentos de uma aula
     */
    public List<Lancamento> findByAula(String aulaId) {
        return new ArrayList<>(store.getOrDefault(aulaId, List.of()));
    }

    /**
     * Limpa o repositório (útil para testes)
     */
    public void clear() {
        store.clear();
    }
}
