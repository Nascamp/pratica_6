package com.example.Pratica4.DevOps;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CursoFiltroServiceTest {

    private List<Curso> massa() {
        return List.of(
            new Curso("ML-101", "Machine Learning Live", Modalidade.AO_VIVO, 20),
            new Curso("DS-201", "Data Science Live",     Modalidade.AO_VIVO, 0),
            new Curso("AI-300", "Inteligência Artificial", Modalidade.EAD_GRAVADO, 50)
        );
    }

    @Test
    void deveFiltrarSomenteAoVivo_eCalcularPainel() {
        // Arrange
        var service = new CursoFiltroService();
        var todos = massa();

        // Action
        var exibidos = service.filtrarPorModalidade(todos, Modalidade.AO_VIVO);
        var painel = service.calcularPainel(exibidos);

        // Assert lista
        var codigos = exibidos.stream().map(Curso::getCodigo).collect(Collectors.toList());
        assertEquals(List.of("ML-101", "DS-201"), codigos);
        assertTrue(exibidos.stream().allMatch(c -> c.getModalidade() == Modalidade.AO_VIVO));
        assertTrue(exibidos.stream().noneMatch(c -> c.getModalidade() == Modalidade.EAD_GRAVADO));

        // Assert painel
        assertEquals(2, painel.getTotalCursosExibidos());
        assertEquals(10, painel.getProgressoMedioPercentual()); // (20 + 0) / 2 = 10
    }

    @Test
    void deveFiltrarSomenteEADGravado_eCalcularPainel() {
        // Arrange
        var service = new CursoFiltroService();
        var todos = massa();

        // Action
        var exibidos = service.filtrarPorModalidade(todos, Modalidade.EAD_GRAVADO);
        var painel = service.calcularPainel(exibidos);

        // Assert lista
        var codigos = exibidos.stream().map(Curso::getCodigo).collect(Collectors.toList());
        assertEquals(List.of("AI-300"), codigos);
        assertTrue(exibidos.stream().allMatch(c -> c.getModalidade() == Modalidade.EAD_GRAVADO));
        assertTrue(exibidos.stream().noneMatch(c -> c.getModalidade() == Modalidade.AO_VIVO));

        // Assert painel
        assertEquals(1, painel.getTotalCursosExibidos());
        assertEquals(50, painel.getProgressoMedioPercentual());
    }
}
