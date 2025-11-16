package com.example.Pratica4.DevOps.application.usecases;

import com.example.Pratica4.DevOps.application.dtos.LancamentoDTOInput;
import com.example.Pratica4.DevOps.application.dtos.ResultadoLancamentoDTOOutput;
import com.example.Pratica4.DevOps.domain.entities.Lancamento;
import com.example.Pratica4.DevOps.domain.aggregates.ResultadoLancamento;
import com.example.Pratica4.DevOps.domain.services.DiarioClasseService;

import java.util.List;

/**
 * Use Case: ProcessarLoteLancamentosUseCase
 * Implementa o caso de uso para processar um lote de lançamentos (Clean Architecture).
 * Orquestra chamadas entre camadas de Application e Domain.
 */
public class ProcessarLoteLancamentosUseCase {
    
    private final DiarioClasseService diarioClasseService;

    public ProcessarLoteLancamentosUseCase(DiarioClasseService diarioClasseService) {
        if (diarioClasseService == null) {
            throw new IllegalArgumentException("DiarioClasseService não pode ser nulo");
        }
        this.diarioClasseService = diarioClasseService;
    }

    /**
     * Executa o caso de uso: processa um lote de lançamentos.
     *
     * @param aulaId identificador da aula
     * @param lancamentosInput lista de DTOs de entrada
     * @return DTO de saída com resultado do processamento
     */
    public ResultadoLancamentoDTOOutput executar(String aulaId, List<LancamentoDTOInput> lancamentosInput) {
        try {
            // Converter DTOs de entrada para entidades de domínio
            List<Lancamento> lancamentos = converterParaEntidades(lancamentosInput);

            // Chamar o serviço de domínio
            ResultadoLancamento resultado = diarioClasseService.processarLote(aulaId, lancamentos);

            // Converter resultado de domínio para DTO de saída
            return converterParaDTO(resultado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao processar lote: " + e.getMessage(), e);
        }
    }

    /**
     * Converte DTOs de entrada para entidades de domínio Lancamento.
     */
    private List<Lancamento> converterParaEntidades(List<LancamentoDTOInput> dtos) {
        return dtos.stream()
                .map(dto -> new Lancamento(dto.getAlunoId(), dto.getNotaValor(), dto.isPresente()))
                .toList();
    }

    /**
     * Converte resultado do domínio para DTO de saída.
     */
    private ResultadoLancamentoDTOOutput converterParaDTO(ResultadoLancamento resultado) {
        return new ResultadoLancamentoDTOOutput(
            resultado.getTotalLancamentos(),
            resultado.getMediaNotas(),
            resultado.getPercentualPresenca(),
            "SUCESSO"
        );
    }
}
