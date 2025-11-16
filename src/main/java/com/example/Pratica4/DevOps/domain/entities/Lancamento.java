package com.example.Pratica4.DevOps.domain.entities;

import com.example.Pratica4.DevOps.domain.valueobjects.Nota;

/**
 * Entity: Lancamento
 * Representa um lançamento de nota e frequência de um aluno em uma aula.
 */
public class Lancamento {
    private final String alunoId;
    private final Nota nota;
    private final boolean presente;

    public Lancamento(String alunoId, Double notaValor, boolean presente) {
        if (alunoId == null || alunoId.isBlank()) {
            throw new IllegalArgumentException("ID do aluno obrigatório");
        }
        
        this.alunoId = alunoId;
        this.nota = new Nota(notaValor);
        this.presente = presente;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public Double getNota() {
        return nota.getValor();
    }

    public boolean isPresente() {
        return presente;
    }

    public boolean isAprovado(Double notaMinima) {
        return nota.isAprovado(notaMinima);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return alunoId.equals(that.alunoId);
    }

    @Override
    public int hashCode() {
        return alunoId.hashCode();
    }
}
