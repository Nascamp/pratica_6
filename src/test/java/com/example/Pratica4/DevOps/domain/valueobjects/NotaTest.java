package com.example.Pratica4.DevOps.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para Nota Value Object
 */
class NotaTest {

    @Test
    void deveCriarNotaComValorValido() {
        Nota n = new Nota(7.5);
        assertEquals(7.5, n.getValor());
    }

    @Test
    void deveLancarExcecao_quandoNotaMenorQueZero() {
        assertThrows(IllegalArgumentException.class,
            () -> new Nota(-0.1)
        );
    }

    @Test
    void deveLancarExcecao_quandoNotaMaiorQue10() {
        assertThrows(IllegalArgumentException.class,
            () -> new Nota(10.1)
        );
    }

    @Test
    void deveLancarExcecao_quandoNotaNula() {
        assertThrows(IllegalArgumentException.class,
            () -> new Nota(null)
        );
    }

    @Test
    void deveValidarAprovacao() {
        Nota n = new Nota(7.0);
        assertTrue(n.isAprovado(7.0));
        assertTrue(n.isAprovado(5.0));
        assertFalse(n.isAprovado(8.0));
    }

    @Test
    void deveAceitarNotasExtremas() {
        Nota min = new Nota(0.0);
        assertEquals(0.0, min.getValor());
        
        Nota max = new Nota(10.0);
        assertEquals(10.0, max.getValor());
    }
}
