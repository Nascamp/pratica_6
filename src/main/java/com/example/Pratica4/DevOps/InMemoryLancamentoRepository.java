package com.example.Pratica4.DevOps;

import java.util.*;

public class InMemoryLancamentoRepository implements LancamentoRepository {
    private final Map<String, List<Lancamento>> store = new HashMap<>();

    @Override
    public void saveAll(String aulaId, List<Lancamento> itens) {
        store.computeIfAbsent(aulaId, k -> new ArrayList<>()).addAll(itens);
    }

    @Override
    public int countByAula(String aulaId) {
        return store.getOrDefault(aulaId, List.of()).size();
    }
}
