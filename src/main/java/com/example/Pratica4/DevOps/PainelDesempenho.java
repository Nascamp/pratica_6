package com.example.Pratica4.DevOps;

public class PainelDesempenho {
    private final int totalCursosExibidos;
    private final int progressoMedioPercentual;

    public PainelDesempenho(int totalCursosExibidos, int progressoMedioPercentual) {
        this.totalCursosExibidos = totalCursosExibidos;
        this.progressoMedioPercentual = progressoMedioPercentual;
    }

    public int getTotalCursosExibidos() { return totalCursosExibidos; }
    public int getProgressoMedioPercentual() { return progressoMedioPercentual; }
}
