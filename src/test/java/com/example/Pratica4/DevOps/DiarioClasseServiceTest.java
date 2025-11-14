package com.example.Pratica4.DevOps;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DiarioClasseServiceTest {

    @Test
    void deveLancarNotasEFrequencia_emLote_comResumo_ePersistencia() {
        // Arrange
        InMemoryLancamentoRepository repo = new InMemoryLancamentoRepository();
        DiarioClasseService service = new DiarioClasseService(repo);
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
        InMemoryLancamentoRepository repo = new InMemoryLancamentoRepository();
        DiarioClasseService service = new DiarioClasseService(repo);
        String aulaId = "AULA-002";
        List<Lancamento> loteInvalido = List.of(
            new Lancamento("ALU-1", 9.5, true),
            new Lancamento("ALU-2", -1.0, false) // inválida
        );

        // Action + Assert
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.processarLote(aulaId, loteInvalido)
        );
        assertTrue(ex.getMessage().contains("nota inválida"));

        // Garantir que NADA foi salvo (atomicidade)
        assertEquals(0, repo.countByAula(aulaId));
    }

    @Test
    void naoDevePersistir_quandoAulaIdVazio_ouLoteVazio() {
        InMemoryLancamentoRepository repo = new InMemoryLancamentoRepository();
        DiarioClasseService service = new DiarioClasseService(repo);

        // aulaId inválido
        assertThrows(IllegalArgumentException.class,
            () -> service.processarLote("  ", List.of(new Lancamento("ALU", 7.0, true)))
        );

        // lote vazio
        assertThrows(IllegalArgumentException.class,
            () -> service.processarLote("AULA-003", List.of())
        );
    }
}
