Prática 4 – DevOps / ATDD-TDD
README – User Story + BDDs (Integrante 4 – Natale)

Tema: Seleção de modalidade de estudo (Ao Vivo vs. EAD Gravado) para adequar a rotina do aluno.

--User Story (Integrante 4 – Natale – Selecionada)

Como aluno
Quero selecionar a modalidade que vou estudar (Ao Vivo ou EAD Gravado)
Para adequar minha grade da melhor forma à minha rotina

Observações de valor

Ao Vivo: exige encaixe na agenda (datas/horários), interação síncrona.

EAD Gravado: flexível, foco em duração/progresso, início imediato.

--Critérios de Aceitação (visão geral)

Catálogo permite filtrar por modalidade (“Ao Vivo”/“EAD”).

Ao aplicar o filtro, somente cursos da modalidade escolhida são listados.

Persistência do filtro: ao retornar ao catálogo, a última modalidade permanece marcada.

Detalhe do curso Ao Vivo exibe calendário de turmas no fuso do aluno.

Detalhe do curso EAD exibe “Iniciar agora” e progresso rastreado.

O sistema evita conflito de horários entre cursos Ao Vivo e sugere alternativas.

Quando não houver turmas Ao Vivo, exibe “Nenhum curso encontrado” e sugere limpar filtro ou ver EAD.

Acessibilidade: é possível alternar modalidades usando teclado/leitor de tela (WCAG).
