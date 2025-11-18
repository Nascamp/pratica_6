package com.example.Pratica4.DevOps.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO Output: ResultadoLancamentoDTOOutput
 * Representa dados de saída após processar lançamentos (Clean Architecture - Use Case Output).
 * Desacopla a camada de apresentação da camada de domínio.
 */
@Data
@AllArgsConstructor
public class ResultadoLancamentoDTOOutput {
    private final int totalLancamentos;
    private final double mediaNotas;
    private final double percentualPresenca;
    private final String status;

    public boolean isProcessadoComSucesso() {
        return "SUCESSO".equals(status);
    }
}