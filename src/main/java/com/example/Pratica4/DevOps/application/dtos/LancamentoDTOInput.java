package com.example.Pratica4.DevOps.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO Input: LancamentoDTOInput
 * Representa dados de entrada para criar um Lancamento (Clean Architecture - Use Case Input).
 * Desacopla a camada de apresentação da camada de domínio.
 */
@Data
@AllArgsConstructor
public class LancamentoDTOInput {
    private final String alunoId;
    private final Double notaValor;
    private final boolean presente;
}