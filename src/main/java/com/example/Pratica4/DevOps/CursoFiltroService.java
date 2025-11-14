package com.example.Pratica4.DevOps;

import java.util.List;
import java.util.stream.Collectors;

public class CursoFiltroService {

    public List<Curso> filtrarPorModalidade(List<Curso> cursos, Modalidade modalidade) {
        return cursos.stream()
                .filter(c -> c.getModalidade() == modalidade)
                .collect(Collectors.toList());
    }

    public PainelDesempenho calcularPainel(List<Curso> cursosFiltrados) {
        int total = cursosFiltrados.size();
        double media = cursosFiltrados.stream()
                .mapToInt(Curso::getProgresso)
                .average()
                .orElse(0.0);
        int mediaArredondada = (int) Math.round(media);
        return new PainelDesempenho(total, mediaArredondada);
    }
}
