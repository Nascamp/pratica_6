package com.example.Pratica4.DevOps;

public class ResultadoLancamento {
    private final int totalLancamentos;
    private final double mediaNotas;
    private final double percentualPresenca; // 0..100

    public ResultadoLancamento(int totalLancamentos, double mediaNotas, double percentualPresenca) {
        this.totalLancamentos = totalLancamentos;
        this.mediaNotas = mediaNotas;
        this.percentualPresenca = percentualPresenca;
    }

    public int getTotalLancamentos() { return totalLancamentos; }
    public double getMediaNotas() { return mediaNotas; }
    public double getPercentualPresenca() { return percentualPresenca; }
}
