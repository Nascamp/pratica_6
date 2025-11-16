package com.example.Pratica4.DevOps.domain.aggregates;

/**
 * Value Object: ResultadoLancamento
 * Representa o resultado agregado do processamento de um lote de lançamentos.
 */
public class ResultadoLancamento {
    private final int totalLancamentos;
    private final double mediaNotas;
    private final double percentualPresenca;

    public ResultadoLancamento(int totalLancamentos, double mediaNotas, double percentualPresenca) {
        if (totalLancamentos < 0) {
            throw new IllegalArgumentException("Total de lançamentos não pode ser negativo");
        }
        if (mediaNotas < 0 || mediaNotas > 10) {
            throw new IllegalArgumentException("Média de notas deve estar entre 0 e 10");
        }
        if (percentualPresenca < 0 || percentualPresenca > 100) {
            throw new IllegalArgumentException("Percentual de presença deve estar entre 0 e 100%");
        }
        
        this.totalLancamentos = totalLancamentos;
        this.mediaNotas = mediaNotas;
        this.percentualPresenca = percentualPresenca;
    }

    public int getTotalLancamentos() {
        return totalLancamentos;
    }

    public double getMediaNotas() {
        return mediaNotas;
    }

    public double getPercentualPresenca() {
        return percentualPresenca;
    }

    public boolean temAlgunsAusentes() {
        return percentualPresenca < 100;
    }

    public boolean todosPresentes() {
        return percentualPresenca == 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultadoLancamento resultado = (ResultadoLancamento) o;
        return totalLancamentos == resultado.totalLancamentos &&
               Double.compare(resultado.mediaNotas, mediaNotas) == 0 &&
               Double.compare(resultado.percentualPresenca, percentualPresenca) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(mediaNotas);
        int result = 31 * totalLancamentos + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(percentualPresenca);
        return 31 * result + (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return String.format("Resultado{total=%d, media=%.2f, presença=%.2f%%}",
                totalLancamentos, mediaNotas, percentualPresenca);
    }
}
