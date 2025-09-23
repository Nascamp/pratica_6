package com.example.Pratica4.DevOps;

import java.util.List;

public class DiarioClasseService {

    private final LancamentoRepository repository;

    public DiarioClasseService(LancamentoRepository repository) {
        this.repository = repository;
    }

    /**
     * Processa um lote de lançamentos de uma aula. Valida tudo antes de salvar.
     * Se houver qualquer dado inválido, lança exceção e nada é persistido.
     */
    public ResultadoLancamento processarLote(String aulaId, List<Lancamento> lote) {
        validar(aulaId, lote);

        int total = lote.size();
        double somaNotas = 0.0;
        int presentes = 0;

        for (Lancamento l : lote) {
            somaNotas += l.getNota();
            if (l.isPresente()) presentes++;
        }

        double mediaNotas = total == 0 ? 0.0 : somaNotas / total;
        double percentualPresenca = total == 0 ? 0.0 : (presentes * 100.0 / total);

        // "integrado": salva em lote somente após validar tudo
        repository.saveAll(aulaId, lote);

        return new ResultadoLancamento(total, mediaNotas, percentualPresenca);
    }

    private void validar(String aulaId, List<Lancamento> lote) {
        if (aulaId == null || aulaId.isBlank()) {
            throw new IllegalArgumentException("aulaId obrigatório");
        }
        if (lote == null || lote.isEmpty()) {
            throw new IllegalArgumentException("lote vazio");
        }
        for (Lancamento l : lote) {
            if (l.getAlunoId() == null || l.getAlunoId().isBlank()) {
                throw new IllegalArgumentException("alunoId obrigatório");
            }
            Double n = l.getNota();
            if (n == null || n < 0.0 || n > 10.0) {
                throw new IllegalArgumentException("nota inválida: " + n);
            }
        }
    }
}
