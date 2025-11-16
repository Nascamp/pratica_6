package com.example.Pratica4.DevOps.domain.services;

import com.example.Pratica4.DevOps.domain.entities.Curso;
import com.example.Pratica4.DevOps.domain.valueobjects.Modalidade;
import com.example.Pratica4.DevOps.domain.aggregates.PainelDesempenho;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Domain Service: CursoFiltroService
 * Serviço de domínio responsável por filtrar cursos e calcular painéis de desempenho.
 */
public class CursoFiltroService {

    /**
     * Filtra cursos pela modalidade especificada.
     * @param cursos lista de cursos
     * @param modalidade modalidade para filtrar
     * @return lista de cursos filtrados
     */
    public List<Curso> filtrarPorModalidade(List<Curso> cursos, Modalidade modalidade) {
        if (cursos == null || modalidade == null) {
            throw new IllegalArgumentException("Parâmetros não podem ser nulos");
        }
        
        return cursos.stream()
                .filter(c -> c.getModalidade() == modalidade)
                .collect(Collectors.toList());
    }

    /**
     * Calcula o painel de desempenho a partir de cursos filtrados.
     * @param cursosFiltrados cursos para análise
     * @return painel com total de cursos e progresso médio
     */
    public PainelDesempenho calcularPainel(List<Curso> cursosFiltrados) {
        if (cursosFiltrados == null) {
            throw new IllegalArgumentException("Lista de cursos não pode ser nula");
        }
        
        int total = cursosFiltrados.size();
        
        if (total == 0) {
            return new PainelDesempenho(0, 0);
        }
        
        double media = cursosFiltrados.stream()
                .mapToInt(Curso::getProgressoPercentual)
                .average()
                .orElse(0.0);
        
        int mediaArredondada = (int) Math.round(media);
        
        return new PainelDesempenho(total, mediaArredondada);
    }
}
