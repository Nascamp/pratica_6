package com.example.Pratica4.DevOps.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.Pratica4.DevOps.domain.entities.Lancamento;
import com.example.Pratica4.DevOps.domain.aggregates.ResultadoLancamento;
import com.example.Pratica4.DevOps.infrastructure.repositories.InMemoryLancamentoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para DiarioClasseService
 * Valida a lógica de processamento e persistência de lançamentos em lote
 */
class DiarioClasseServiceTest {

    private InMemoryLancamentoRepository repo;
    private DiarioClasseService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryLancamentoRepository();
        service = new DiarioClasseService(repo);
    }

    @Test
    void deveLancarNotasEFrequencia_emLote_comResumo_ePersistencia() {
        // Arrange
        String aulaId = "AULA-001";
        List<Lancamento> lote = List.of(
            new Lancamento("ALU-1", 8.0, true),
            new Lancamento("ALU-2", 7.0, false),
            new Lancamento("ALU-3", 10.0, true)
        );

        // Action
        ResultadoLancamento r = service.processarLote(aulaId, lote);

        // Assert resumo
        assertEquals(3, r.getTotalLancamentos());
        assertEquals(8.333, r.getMediaNotas(), 0.01);     // (8+7+10)/3
        assertEquals(66.667, r.getPercentualPresenca(), 0.1); // 2/3 * 100

        // Assert persistência integrada (salvou todos)
        assertEquals(3, repo.countByAula(aulaId));
    }

    @Test
    void naoDevePersistir_quandoNotaInvalida_noLote() {
        // Arrange
        String aulaId = "AULA-002";
        List<Lancamento> loteInvalido = List.of(
            new Lancamento("ALU-1", 9.5, true),
            new Lancamento("ALU-2", -1.0, false) // nota inválida
        );

        // Action + Assert
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.processarLote(aulaId, loteInvalido)
        );
        assertTrue(ex.getMessage().contains("nota"));

        // Garantir que NADA foi salvo (atomicidade)
        assertEquals(0, repo.countByAula(aulaId));
    }

    @Test
    void naoDevePersistir_quandoAulaIdVazio_ouLoteVazio() {
        // aulaId inválido
        assertThrows(IllegalArgumentException.class,
            () -> service.processarLote("  ", List.of(new Lancamento("ALU", 7.0, true)))
        );

        // lote vazio
        assertThrows(IllegalArgumentException.class,
            () -> service.processarLote("AULA-003", List.of())
        );
    }

    @Test
    void deveProcessarLoteComTodosPresentes() {
        // Arrange
        String aulaId = "AULA-004";
        List<Lancamento> lote = List.of(
            new Lancamento("ALU-1", 9.0, true),
            new Lancamento("ALU-2", 8.5, true),
            new Lancamento("ALU-3", 10.0, true)
        );

        // Action
        ResultadoLancamento r = service.processarLote(aulaId, lote);

        // Assert
        assertTrue(r.todosPresentes());
        assertEquals(100, r.getPercentualPresenca(), 0.01);
        assertEquals(9.167, r.getMediaNotas(), 0.01);
    }

    @Test
    void deveProcessarLoteComAlgunsAusentes() {
        // Arrange
        String aulaId = "AULA-005";
        List<Lancamento> lote = List.of(
            new Lancamento("ALU-1", 9.0, true),
            new Lancamento("ALU-2", 8.0, false),
            new Lancamento("ALU-3", 7.0, false)
        );

        // Action
        ResultadoLancamento r = service.processarLote(aulaId, lote);

        // Assert
        assertTrue(r.temAlgunsAusentes());
        assertEquals(33.333, r.getPercentualPresenca(), 0.1);
    }

    @Test
    void deveLancarExcecao_quandoRepositoryNulo() {
        assertThrows(IllegalArgumentException.class,
            () -> new DiarioClasseService(null)
        );
    }
}
