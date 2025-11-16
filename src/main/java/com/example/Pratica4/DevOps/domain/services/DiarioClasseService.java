package com.example.Pratica4.DevOps.domain.services;

import com.example.Pratica4.DevOps.domain.entities.Lancamento;
import com.example.Pratica4.DevOps.domain.aggregates.ResultadoLancamento;
import com.example.Pratica4.DevOps.domain.ports.LancamentoRepository;
import java.util.List;

/**
 * Domain Service: DiarioClasseService
 * Serviço de domínio responsável pela lógica de processamento de lançamentos em lote.
 * Implementa as regras de negócio críticas para diários de classe.
 */
public class DiarioClasseService {

    private final LancamentoRepository repository;

    public DiarioClasseService(LancamentoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository não pode ser nulo");
        }
        this.repository = repository;
    }

    /**
     * Processa um lote de lançamentos de uma aula.
     * Valida todos os dados antes de salvar (transação atômica).
     * Se houver qualquer dado inválido, lança exceção e nada é persistido.
     *
     * @param aulaId identificador único da aula
     * @param lote lista de lançamentos a processar
     * @return resultado com estatísticas do processamento
     * @throws IllegalArgumentException se dados inválidos
     */
    public ResultadoLancamento processarLote(String aulaId, List<Lancamento> lote) {
        validarEntrada(aulaId, lote);

        int total = lote.size();
        double somaNotas = calcularSomaNotas(lote);
        int presentes = contarPresentes(lote);

        double mediaNotas = calcularMedia(somaNotas, total);
        double percentualPresenca = calcularPercentualPresenca(presentes, total);

        // Salva em lote apenas após validar tudo (atomicidade)
        repository.saveAll(aulaId, lote);

        return new ResultadoLancamento(total, mediaNotas, percentualPresenca);
    }

    /**
     * Valida os dados de entrada do lote.
     */
    private void validarEntrada(String aulaId, List<Lancamento> lote) {
        if (aulaId == null || aulaId.isBlank()) {
            throw new IllegalArgumentException("Identificador da aula obrigatório");
        }
        if (lote == null || lote.isEmpty()) {
            throw new IllegalArgumentException("Lote de lançamentos não pode estar vazio");
        }
    }

    /**
     * Calcula a soma das notas do lote.
     */
    private double calcularSomaNotas(List<Lancamento> lote) {
        return lote.stream()
                .mapToDouble(Lancamento::getNota)
                .sum();
    }

    /**
     * Conta a quantidade de alunos presentes.
     */
    private int contarPresentes(List<Lancamento> lote) {
        return (int) lote.stream()
                .filter(Lancamento::isPresente)
                .count();
    }

    /**
     * Calcula a média de notas.
     */
    private double calcularMedia(double soma, int total) {
        return total == 0 ? 0.0 : soma / total;
    }

    /**
     * Calcula o percentual de presença.
     */
    private double calcularPercentualPresenca(int presentes, int total) {
        return total == 0 ? 0.0 : (presentes * 100.0 / total);
    }
}
