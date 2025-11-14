package com.example.Pratica4.DevOps;

public class Curso {
    private final String codigo;
    private final String titulo;
    private final Modalidade modalidade;
    private final int progresso; // 0..100

    public Curso(String codigo, String titulo, Modalidade modalidade, int progresso) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.modalidade = modalidade;
        this.progresso = progresso;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public Modalidade getModalidade() { return modalidade; }
    public int getProgresso() { return progresso; }
}
