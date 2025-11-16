# Arquitetura Clean Architecture + DDD

Este projeto implementa **Clean Architecture** combinada com **Domain-Driven Design (DDD)** para criar uma estrutura robusta, testável e escalável.

## Estrutura de Camadas

```
project/
├── src/main/java/com/example/Pratica4/DevOps/
│   ├── domain/                          # Domain Layer (Regras de Negócio)
│   │   ├── entities/                    # Entidades de Domínio
│   │   ├── aggregates/                  # Agregados
│   │   ├── valueobjects/                # Value Objects
│   │   ├── ports/                       # Interfaces de Domínio (Abstrações)
│   │   └── services/                    # Domain Services (Lógica de Negócio)
│   │
│   ├── application/                     # Application Layer (Casos de Uso)
│   │   ├── dtos/                        # Data Transfer Objects
│   │   ├── usecases/                    # Casos de Uso (Use Cases)
│   │   └── services/                    # Application Services
│   │
│   ├── infrastructure/                  # Infrastructure Layer (Implementações)
│   │   └── repositories/                # Implementações de Repositórios
│   │
│   └── config/                          # Configuration Layer (Setup)
│
└── src/test/java/com/example/Pratica4/DevOps/
    ├── domain/                          # Testes de Domínio
    └── application/                     # Testes de Aplicação
```

## Camadas e Responsabilidades

### 1. **Domain Layer** (Camada de Domínio)
Contém a lógica de negócio central e regras que devem ser protegidas:

- **Entities**: Objetos com identidade única (ex: `Lancamento`, `Curso`)
- **Value Objects**: Objetos sem identidade (ex: `Nota`, `Progresso`, `Modalidade`)
- **Aggregates**: Conjunto de entidades relacionadas (ex: `ResultadoLancamento`)
- **Domain Services**: Orquestram lógica complexa entre múltiplas entidades
- **Ports (Interfaces)**: Abstraem dependências externas (ex: `LancamentoRepository`)

### 2. **Application Layer** (Camada de Aplicação)
Conecta casos de uso a camadas externas:

- **DTOs**: `LancamentoDTOInput`, `ResultadoLancamentoDTOOutput` - Desacoplam apresentação do domínio
- **Use Cases**: `ProcessarLoteLancamentosUseCase` - Orquestram fluxo de negócio
- **Application Services**: Gerenciam múltiplos casos de uso

### 3. **Infrastructure Layer** (Camada de Infraestrutura)
Implementações concretas de interfaces do domínio:

- **Repositories**: `InMemoryLancamentoRepository` - Persistência
- **Adapters**: Conversores entre camadas

## Fluxo de Dados (Use Case Completo)

```
Controller/UI (External)
         ↓
Application Service (DiarioClasseApplicationService)
         ↓
Use Case (ProcessarLoteLancamentosUseCase)
         ├→ Converte DTO Input → Entidades de Domínio
         ├→ Domain Service (DiarioClasseService)
         │  └→ Executa Regras de Negócio
         ├→ Repository (Port Implementation)
         │  └→ Persiste Dados
         └→ Converte Resultado → DTO Output
         ↓
Application Service (retorna DTO Output)
         ↓
Controller/UI (External)
```

## Exemplo de Uso

### Teste Unitário (Unit Test)
```java
@Test
void deveLancarNotasEmLote() {
    // Arrange
    String aulaId = "AULA-001";
    List<LancamentoDTOInput> lancamentos = List.of(
        new LancamentoDTOInput("ALU-1", 8.0, true),
        new LancamentoDTOInput("ALU-2", 7.0, false)
    );
    
    // Act
    ResultadoLancamentoDTOOutput resultado = appService.processarLote(aulaId, lancamentos);
    
    // Assert
    assertEquals("SUCESSO", resultado.getStatus());
    assertEquals(2, resultado.getTotalLancamentos());
}
```

## Benefícios dessa Arquitetura

✅ **Independência de Frameworks**: Lógica de negócio não depende de Spring, JPA, etc.  
✅ **Testabilidade**: Fácil testar em isolamento com mocks  
✅ **Manutenibilidade**: Separação clara de responsabilidades  
✅ **Escalabilidade**: Fácil adicionar novos casos de uso  
✅ **Reutilização**: Domain Services podem ser usados em diferentes contextos  
✅ **Entendimento**: Código expressivo e próximo da linguagem de negócio  

## Princípios SOLID Aplicados

- **S**ingle Responsibility: Cada classe tem uma responsabilidade
- **O**pen/Closed: Aberto para extensão via interfaces (Ports)
- **L**iskov Substitution: Implementações são intercambiáveis
- **I**nterface Segregation: Interfaces específicas por necessidade
- **D**ependency Inversion: Dependências injetadas, não instanciadas

## Testes

Executar todos os testes:
```bash
mvn clean test
```

Estrutura de testes:
- `NotaTest`: Valida regras do Value Object Nota
- `ProgressoTest`: Valida regras do Value Object Progresso
- `DiarioClasseServiceTest`: Valida lógica do serviço de domínio
- `CursoFiltroServiceTest`: Valida filtragem de cursos
