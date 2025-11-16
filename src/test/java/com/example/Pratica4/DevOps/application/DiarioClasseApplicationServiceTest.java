package com.example.Pratica4.DevOps.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.Pratica4.DevOps.application.dtos.LancamentoDTOInput;
import com.example.Pratica4.DevOps.application.dtos.ResultadoLancamentoDTOOutput;
import com.example.Pratica4.DevOps.application.services.DiarioClasseApplicationService;
import com.example.Pratica4.DevOps.domain.services.DiarioClasseService;
import com.example.Pratica4.DevOps.infrastructure.repositories.InMemoryLancamentoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para DiarioClasseApplicationService
 * Valida a camada de Application (Use Cases e orquestração)
 */
class DiarioClasseApplicationServiceTest {

    private DiarioClasseApplicationService appService;
    private InMemoryLancamentoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLancamentoRepository();
        DiarioClasseService domainService = new DiarioClasseService(repository);
        appService = new DiarioClasseApplicationService(domainService);
    }

    @Test
    void deveProcessarLotePorIntermedioDTOs() {
        // Arrange
        String aulaId = "AULA-001";
        List<LancamentoDTOInput> lancamentos = List.of(
            new LancamentoDTOInput("ALU-1", 8.0, true),
            new LancamentoDTOInput("ALU-2", 7.0, false),
            new LancamentoDTOInput("ALU-3", 9.0, true)
        );

        // Act
        ResultadoLancamentoDTOOutput resultado = appService.processarLote(aulaId, lancamentos);

        // Assert
        assertTrue(resultado.isProcessadoComSucesso());
        assertEquals(3, resultado.getTotalLancamentos());
        assertEquals(8.0, resultado.getMediaNotas(), 0.01);
        assertEquals(66.667, resultado.getPercentualPresenca(), 0.1);
    }

    @Test
    void deveValidarAulaIdVazio() {
        List<LancamentoDTOInput> lancamentos = List.of(
            new LancamentoDTOInput("ALU-1", 8.0, true)
        );

        assertThrows(IllegalArgumentException.class,
            () -> appService.processarLote("  ", lancamentos)
        );
    }

    @Test
    void deveValidarLoteVazio() {
        assertThrows(IllegalArgumentException.class,
            () -> appService.processarLote("AULA-001", List.of())
        );
    }

    @Test
    void deveConverterNotaInvalidaEmExcecao() {
        String aulaId = "AULA-002";
        List<LancamentoDTOInput> lancamentos = List.of(
            new LancamentoDTOInput("ALU-1", -5.0, true) // nota inválida
        );

        assertThrows(IllegalArgumentException.class,
            () -> appService.processarLote(aulaId, lancamentos)
        );
    }

    @Test
    void deveProcessarLoteComTodos100Porcento() {
        String aulaId = "AULA-003";
        List<LancamentoDTOInput> lancamentos = List.of(
            new LancamentoDTOInput("ALU-1", 10.0, true),
            new LancamentoDTOInput("ALU-2", 9.5, true),
            new LancamentoDTOInput("ALU-3", 9.0, true)
        );

        ResultadoLancamentoDTOOutput resultado = appService.processarLote(aulaId, lancamentos);

        assertEquals(100.0, resultado.getPercentualPresenca());
        assertEquals(9.5, resultado.getMediaNotas(), 0.01);
    }
}
