package com.example.Pratica4.DevOps.domain.ports;

import java.util.List;
import com.example.Pratica4.DevOps.domain.entities.Lancamento;

/**
 * Port: LancamentoRepository
 * Interface do repositório (contrato para a camada de infraestrutura).
 * Define operações de persistência para Lancamento.
 */
public interface LancamentoRepository {
    /**
     * Persiste um lote de lançamentos associados a uma aula.
     */
    void saveAll(String aulaId, List<Lancamento> itens);

    /**
     * Conta a quantidade de lançamentos registrados para uma aula.
     */
    int countByAula(String aulaId);
}
