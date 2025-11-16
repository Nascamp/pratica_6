package com.example.Pratica4.DevOps.domain.valueobjects;

/**
 * Value Object: Progresso
 * Representa o progresso de um curso (0-100%).
 * Encapsula validações e lógica relacionada a progresso.
 */
public class Progresso {
    private static final int MIN_PERCENTUAL = 0;
    private static final int MAX_PERCENTUAL = 100;
    private static final int PERCENTUAL_CONCLUSAO = 100;

    private final int valor;

    public Progresso(int valor) {
        if (valor < MIN_PERCENTUAL || valor > MAX_PERCENTUAL) {
            throw new IllegalArgumentException("Progresso deve estar entre 0 e 100%");
        }
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public boolean isConcluido() {
        return valor == PERCENTUAL_CONCLUSAO;
    }

    public boolean isIniciado() {
        return valor > MIN_PERCENTUAL;
    }

    public int percentualRestante() {
        return MAX_PERCENTUAL - valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progresso progresso = (Progresso) o;
        return valor == progresso.valor;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(valor);
    }

    @Override
    public String toString() {
        return valor + "%";
    }
}
