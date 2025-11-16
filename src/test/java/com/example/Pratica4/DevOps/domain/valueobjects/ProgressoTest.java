package com.example.Pratica4.DevOps.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para Progresso Value Object
 */
class ProgressoTest {

    @Test
    void deveCriarProgressoComValorValido() {
        Progresso p = new Progresso(50);
        assertEquals(50, p.getValor());
    }

    @Test
    void deveLancarExcecao_quandoProgressoMenorQueZero() {
        assertThrows(IllegalArgumentException.class,
            () -> new Progresso(-1)
        );
    }

    @Test
    void deveLancarExcecao_quandoProgressoMaiorQue100() {
        assertThrows(IllegalArgumentException.class,
            () -> new Progresso(101)
        );
    }

    @Test
    void deveIdentificarConcluidoEm100Porcento() {
        Progresso p = new Progresso(100);
        assertTrue(p.isConcluido());
    }

    @Test
    void deveIdentificarNaoConcluidoEmMenosDe100() {
        Progresso p = new Progresso(99);
        assertFalse(p.isConcluido());
    }

    @Test
    void deveIdentificarIniciadoQuandoMaiorQueZero() {
        Progresso p = new Progresso(1);
        assertTrue(p.isIniciado());
        
        Progresso p0 = new Progresso(0);
        assertFalse(p0.isIniciado());
    }

    @Test
    void deveCalcularPercentualRestante() {
        Progresso p = new Progresso(30);
        assertEquals(70, p.percentualRestante());
    }
}
