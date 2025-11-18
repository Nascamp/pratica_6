package com.example.Pratica4.DevOps.infrastructure.entities;

import jakarta.persistence.*;

/**
 * JPA Entity mapping for Curso (infrastructure layer).
 */
@Entity
@Table(name = "cursos")
public class CursoEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String modalidade;

    @Column(name = "progresso_percentual", nullable = false)
    private int progressoPercentual;

    // No-arg constructor required by JPA
    public CursoEntity() {
    }

    // All-args constructor
    public CursoEntity(String codigo, String titulo, String modalidade, int progressoPercentual) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.modalidade = modalidade;
        this.progressoPercentual = progressoPercentual;
    }

    // Getters and setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public int getProgressoPercentual() {
        return progressoPercentual;
    }

    public void setProgressoPercentual(int progressoPercentual) {
        this.progressoPercentual = progressoPercentual;
    }

    @Override
    public String toString() {
        return "CursoEntity{" +
                "codigo='" + codigo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", modalidade='" + modalidade + '\'' +
                ", progressoPercentual=" + progressoPercentual +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CursoEntity that = (CursoEntity) o;

        return codigo != null ? codigo.equals(that.codigo) : that.codigo == null;
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}