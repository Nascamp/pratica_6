package com.example.Pratica4.DevOps;

import java.util.List;

public interface LancamentoRepository {
    void saveAll(String aulaId, List<Lancamento> itens);
    int countByAula(String aulaId);
}
