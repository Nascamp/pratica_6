package com.example.Pratica4.DevOps.domain.aggregates;

/**
 * Value Object: PainelDesempenho
 * Representa o resultado agregado da análise de cursos.
 */
public class PainelDesempenho {
    private final int totalCursosExibidos;
    private final int progressoMedioPercentual;

    public PainelDesempenho(int totalCursosExibidos, int progressoMedioPercentual) {
        if (totalCursosExibidos < 0) {
            throw new IllegalArgumentException("Total de cursos não pode ser negativo");
        }
        if (progressoMedioPercentual < 0 || progressoMedioPercentual > 100) {
            throw new IllegalArgumentException("Progresso médio deve estar entre 0 e 100%");
        }
        
        this.totalCursosExibidos = totalCursosExibidos;
        this.progressoMedioPercentual = progressoMedioPercentual;
    }

    public int getTotalCursosExibidos() {
        return totalCursosExibidos;
    }

    public int getProgressoMedioPercentual() {
        return progressoMedioPercentual;
    }

    public boolean estaSemCursos() {
        return totalCursosExibidos == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PainelDesempenho painel = (PainelDesempenho) o;
        return totalCursosExibidos == painel.totalCursosExibidos &&
               progressoMedioPercentual == painel.progressoMedioPercentual;
    }

    @Override
    public int hashCode() {
        return 31 * totalCursosExibidos + progressoMedioPercentual;
    }

    @Override
    public String toString() {
        return String.format("Painel{cursos=%d, progresso=%d%%}",
                totalCursosExibidos, progressoMedioPercentual);
    }
}
