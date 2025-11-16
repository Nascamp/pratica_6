package com.example.Pratica4.DevOps.application.dtos;

/**
 * DTO Input: LancamentoDTOInput
 * Representa dados de entrada para criar um Lancamento (Clean Architecture - Use Case Input).
 * Desacopla a camada de apresentação da camada de domínio.
 */
public class LancamentoDTOInput {
    private final String alunoId;
    private final Double notaValor;
    private final boolean presente;

    public LancamentoDTOInput(String alunoId, Double notaValor, boolean presente) {
        this.alunoId = alunoId;
        this.notaValor = notaValor;
        this.presente = presente;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public Double getNotaValor() {
        return notaValor;
    }

    public boolean isPresente() {
        return presente;
    }

    @Override
    public String toString() {
        return "LancamentoDTOInput{" +
                "alunoId='" + alunoId + '\'' +
                ", notaValor=" + notaValor +
                ", presente=" + presente +
                '}';
    }
}
