package com.example.Pratica4.DevOps.domain.valueobjects;

/**
 * Value Object: Modalidade
 * Representa a modalidade de um curso (Ao Vivo ou EAD Gravado).
 */
public enum Modalidade {
    AO_VIVO("Ao Vivo"),
    EAD_GRAVADO("EAD Gravado");

    private final String descricao;

    Modalidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
