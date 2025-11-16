# 🔧 Configuração do Projeto no Jenkins

Guia completo para configurar e executar o projeto **Prática 6 - Clean Architecture + DDD** no Jenkins CI/CD.

---

## 📋 Pré-requisitos

### Softwares Necessários
- ✅ Jenkins 2.350+
- ✅ Java 17 ou superior
- ✅ Maven 3.6+
- ✅ Git

### Plugins Jenkins Obrigatórios
```
✅ Pipeline
✅ Git
✅ JUnit
✅ HTML Publisher
✅ Timestamper
✅ AnsiColor (opcional, para cores nos logs)
```

### Como Instalar Plugins
1. Acesse: `Gerenciar Jenkins` → `Gerenciar Plugins`
2. Na aba "Disponíveis", procure por:
   - Pipeline
   - Git
   - JUnit
   - HTML Publisher
   - Timestamper
3. Selecione e clique "Instalar sem reiniciar"

---

## 🚀 Configuração Passo a Passo

### Passo 1: Configurar Java e Maven no Jenkins

1. Acesse: `Gerenciar Jenkins` → `Ferramentas Globais`

2. **Seção: JDK**
   - Nome: `Java17`
   - JAVA_HOME: `/usr/lib/jvm/java-17-openjdk` (Linux) ou `C:\Program Files\Java\jdk-17` (Windows)

3. **Seção: Maven**
   - Nome: `Maven3.9`
   - MAVEN_HOME: `/opt/maven` (Linux) ou `C:\Program Files\apache-maven-3.9` (Windows)

4. Clique "Salvar"

---

### Passo 2: Criar Nova Job Pipeline

1. Clique "Nova Tarefa" no Dashboard
2. Digite o nome: `Pratica-6-Clean-Architecture`
3. Selecione: `Pipeline`
4. Clique "OK"

---

### Passo 3: Configurar Pipeline

#### Opção A: Git (Recomendado)

1. Na seção "Pipeline", escolha: `Pipeline script from SCM`
2. Selecione: `Git`
3. Preencha:
   ```
   Repository URL: https://github.com/seu-usuario/seu-repo.git
   Credentials: (adicione suas credenciais)
   Branch: */main
   Script Path: Jenkinsfile
   ```
4. Clique "Salvar"

#### Opção B: Jenkinsfile Inline (para testes)

1. Na seção "Pipeline", escolha: `Pipeline script`
2. Cole o conteúdo do `Jenkinsfile` (ver abaixo)
3. Clique "Salvar"

---

## 📄 Jenkinsfile Completo

O projeto já inclui um `Jenkinsfile` na raiz com os seguintes stages:

```groovy
✅ Checkout - Clonar repositório
✅ Build - Compilar código
✅ Unit Tests - Rodar testes
✅ Code Quality - Analisar qualidade
✅ Package - Criar JAR
✅ Archive Artifacts - Arquivar saídas
✅ Generate Reports - Gerar relatórios
```

---

## 🎯 Executar Build Manualmente

### Primeira Execução

1. Abra a job: `Pratica-6-Clean-Architecture`
2. Clique: `Executar Agora`
3. Veja o progresso em `Saída do Console`

### Resultado Esperado

```
✅ Tests run: 34, Failures: 0, Errors: 0
✅ BUILD SUCCESS
```

---

## 📊 Monitorar Builds

### Dashboard Jenkins

- **Build Status**: Verde = Sucesso, Vermelho = Falha
- **Build History**: Histórico de todas as builds
- **Trend**: Gráfico de tendências

### Logs e Artefatos

1. Clique no número do build (ex: `#1`)
2. Acesse:
   - `Console Output` - Logs completos
   - `Artefatos` - JARs e XMLs
   - `Test Report` - Relatório de testes

---

## 🔄 Automatizar Builds (CI/CD)

### Opção A: Poll SCM (Verificação Periódica)

1. Acesse a configuração da job
2. Em "Build Triggers", selecione: `Poll SCM`
3. Preencha o Schedule:
   ```
   H/15 * * * *    # A cada 15 minutos
   H H * * *       # Uma vez por dia
   H H * * 1-5     # De segunda a sexta
   ```
4. Salve

### Opção B: GitHub Webhook (Melhor)

1. No GitHub, vá para: `Settings` → `Webhooks`
2. Clique: `Add webhook`
3. Preencha:
   ```
   Payload URL: https://seu-jenkins.com/github-webhook/
   Content type: application/json
   Events: Push events
   Active: ✅
   ```
4. Clique: `Add webhook`

---

## 📈 Relatórios e Métricas

### Testes
- **Arquivo**: `target/surefire-reports/index.html`
- **Métrica**: Taxa de sucesso (deve ser 100%)

### Build History
- **Arquivo**: Painel principal da job
- **Métrica**: Frequência de falhas

### Performance
- **Arquivo**: Console output
- **Métrica**: Tempo de execução

---

## 🐛 Troubleshooting

### Problema 1: Maven não encontrado

**Solução:**
```bash
# Configure em Ferramentas Globais
Gerenciar Jenkins → Ferramentas Globais → Maven
```

### Problema 2: Java não encontrado

**Solução:**
```bash
# Configure em Ferramentas Globais
Gerenciar Jenkins → Ferramentas Globais → JDK
```

### Problema 3: Teste falhando no Jenkins mas não localmente

**Verificar:**
1. Mesma versão de Java
2. Mesma versão de Maven
3. Permissões de arquivo

### Problema 4: Build timeout

**Solução:**
Aumentar timeout no Jenkinsfile:
```groovy
timeout(time: 60, unit: 'MINUTES')  # Alterado de 30 para 60
```

---

## 📝 Configuração Avançada

### Environment Variables

O Jenkinsfile já define:
```groovy
JAVA_HOME = tool 'Java17'
MAVEN_HOME = tool 'Maven3.9'
PROJECT_NAME = 'Pratica-4-DevOps'
```

### Notificações (Opcional)

#### Email
```groovy
post {
    success {
        emailext(
            subject: "Build #${BUILD_NUMBER} SUCCESS",
            body: "Projeto compilado com sucesso!",
            to: "seu-email@exemplo.com"
        )
    }
}
```

#### Slack
```groovy
post {
    success {
        slackSend(
            color: 'good',
            message: "Build #${BUILD_NUMBER} SUCCESS"
        )
    }
}
```

---

## 🔐 Credenciais e Segurança

### Armazenar Credenciais

1. Acesse: `Gerenciar Jenkins` → `Gerenciar Credenciais`
2. Clique: `Armazenar credenciais do sistema`
3. Clique: `Adição de Credenciais`
4. Selecione: `Username with password`
5. Preencha com suas credenciais do GitHub
6. Clique: `Criar`

### Usar Credenciais no Jenkinsfile

```groovy
environment {
    GITHUB_CREDENTIALS = credentials('github-credentials-id')
}
```

---

## 📊 Exemplo de Build Bem-Sucedido

```
[Pipeline] Declarative: Checkout SCM
[Pipeline] node
[Pipeline] Checkout
[Pipeline] Build
[Pipeline] Unit Tests
[Pipeline] Code Quality Analysis
[Pipeline] Package
[Pipeline] Archive Artifacts
[Pipeline] Generate Reports

POST BUILD ACTIONS:
✅ Testes publicados: 34/34 passaram
✅ Artefatos arquivados
✅ Relatório HTML gerado

BUILD SUCCESS - Time: 00:04:32
```

---

## 🎓 Próximas Etapas

1. **Configurar Notificações**: Email ou Slack
2. **Adicionar SonarQube**: Análise estática
3. **Configurar Deploy**: Para ambiente staging
4. **Adicionar Testes de Integração**: DB real
5. **Configurar Security Scan**: OWASP dependency-check

---

## 📞 Referências

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Declarative Pipeline](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [Maven Integration](https://wiki.jenkins.io/display/JENKINS/Maven+Project+Plugin)
- [Git Plugin](https://plugins.jenkins.io/git/)

---

## ✅ Checklist de Configuração

- [ ] Jenkins instalado e rodando
- [ ] Plugins instalados (Pipeline, Git, JUnit, HTML Publisher)
- [ ] Java 17 configurado em Ferramentas Globais
- [ ] Maven 3.9 configurado em Ferramentas Globais
- [ ] Nova job Pipeline criada
- [ ] Jenkinsfile adicionado ao repositório
- [ ] Primeira build executada com sucesso
- [ ] Relatórios gerando corretamente
- [ ] Webhooks configurados (opcional)
- [ ] Notificações configuradas (opcional)

---

**Pronto! Seu projeto agora roda no Jenkins! 🚀**
