package com.example.Pratica4.DevOps;

public class Lancamento {
    private final String alunoId;
    private final Double nota;      // 0..10
    private final boolean presente; // presença do aluno na aula

    public Lancamento(String alunoId, Double nota, boolean presente) {
        this.alunoId = alunoId;
        this.nota = nota;
        this.presente = presente;
    }

    public String getAlunoId() { return alunoId; }
    public Double getNota() { return nota; }
    public boolean isPresente() { return presente; }
}



