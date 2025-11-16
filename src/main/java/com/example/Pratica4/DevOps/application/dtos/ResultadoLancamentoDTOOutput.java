package com.example.Pratica4.DevOps.application.dtos;

/**
 * DTO Output: ResultadoLancamentoDTOOutput
 * Representa dados de saída após processar lançamentos (Clean Architecture - Use Case Output).
 * Desacopla a camada de apresentação da camada de domínio.
 */
public class ResultadoLancamentoDTOOutput {
    private final int totalLancamentos;
    private final double mediaNotas;
    private final double percentualPresenca;
    private final String status;

    public ResultadoLancamentoDTOOutput(int totalLancamentos, double mediaNotas, 
                                       double percentualPresenca, String status) {
        this.totalLancamentos = totalLancamentos;
        this.mediaNotas = mediaNotas;
        this.percentualPresenca = percentualPresenca;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public boolean isProcessadoComSucesso() {
        return "SUCESSO".equals(status);
    }

    @Override
    public String toString() {
        return "ResultadoLancamentoDTOOutput{" +
                "totalLancamentos=" + totalLancamentos +
                ", mediaNotas=" + mediaNotas +
                ", percentualPresenca=" + percentualPresenca +
                ", status='" + status + '\'' +
                '}';
    }
}
