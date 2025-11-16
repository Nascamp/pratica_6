package com.example.Pratica4.DevOps.domain.valueobjects;

/**
 * Value Object: Nota
 * Encapsula uma nota (0-10) com validações.
 */
public class Nota {
    private static final double MIN_NOTA = 0.0;
    private static final double MAX_NOTA = 10.0;

    private final Double valor;

    public Nota(Double valor) {
        if (valor == null || valor < MIN_NOTA || valor > MAX_NOTA) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public boolean isAprovado(Double notaMinima) {
        return valor >= notaMinima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota = (Nota) o;
        return valor.equals(nota.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%.2f", valor);
    }
}
