package com.example.Pratica4.DevOps.application.services;

import com.example.Pratica4.DevOps.application.dtos.LancamentoDTOInput;
import com.example.Pratica4.DevOps.application.dtos.ResultadoLancamentoDTOOutput;
import com.example.Pratica4.DevOps.application.usecases.ProcessarLoteLancamentosUseCase;
import com.example.Pratica4.DevOps.domain.services.DiarioClasseService;

import java.util.List;

/**
 * Application Service: DiarioClasseApplicationService
 * Serviço de aplicação que orquestra casos de uso (Clean Architecture).
 * Responsável por coordenar entre a camada de Application e Domain.
 */
public class DiarioClasseApplicationService {
    
    private final ProcessarLoteLancamentosUseCase processarLoteLancamentosUseCase;

    public DiarioClasseApplicationService(DiarioClasseService diarioClasseService) {
        if (diarioClasseService == null) {
            throw new IllegalArgumentException("DiarioClasseService não pode ser nulo");
        }
        this.processarLoteLancamentosUseCase = new ProcessarLoteLancamentosUseCase(diarioClasseService);
    }

    /**
     * Processa um lote de lançamentos através do caso de uso.
     *
     * @param aulaId identificador da aula
     * @param lancamentosInput lista de DTOs com dados de entrada
     * @return DTO com resultado do processamento
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public ResultadoLancamentoDTOOutput processarLote(String aulaId, List<LancamentoDTOInput> lancamentosInput) {
        validarEntrada(aulaId, lancamentosInput);
        return processarLoteLancamentosUseCase.executar(aulaId, lancamentosInput);
    }

    /**
     * Valida os dados de entrada do serviço de aplicação.
     */
    private void validarEntrada(String aulaId, List<LancamentoDTOInput> lancamentosInput) {
        if (aulaId == null || aulaId.isBlank()) {
            throw new IllegalArgumentException("Identificador da aula obrigatório");
        }
        if (lancamentosInput == null || lancamentosInput.isEmpty()) {
            throw new IllegalArgumentException("Lista de lançamentos não pode estar vazia");
        }
    }
}
