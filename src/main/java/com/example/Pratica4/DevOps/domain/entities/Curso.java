package com.example.Pratica4.DevOps.domain.entities;

import com.example.Pratica4.DevOps.domain.valueobjects.Modalidade;
import com.example.Pratica4.DevOps.domain.valueobjects.Progresso;

/**
 * Aggregate Root: Curso
 * Representa um curso no domínio, encapsulando suas regras de negócio.
 */
public class Curso {
    private final String codigo;
    private final String titulo;
    private final Modalidade modalidade;
    private final Progresso progresso;

    public Curso(String codigo, String titulo, Modalidade modalidade, int progressoPercentual) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código do curso obrigatório");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título do curso obrigatório");
        }
        if (modalidade == null) {
            throw new IllegalArgumentException("Modalidade obrigatória");
        }
        
        this.codigo = codigo;
        this.titulo = titulo;
        this.modalidade = modalidade;
        this.progresso = new Progresso(progressoPercentual);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public int getProgressoPercentual() {
        return progresso.getValor();
    }

    public boolean isConcluido() {
        return progresso.isConcluido();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return codigo.equals(curso.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
