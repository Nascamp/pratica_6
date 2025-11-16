# Configuração das Pipelines no Jenkins

## 1️⃣ Pipeline: image_docker

### Configuração no Jenkins:
```
Nome: image_docker
Tipo: Pipeline job
```

### Script Path:
```
Jenkinsfile.docker
```

### Variáveis de Ambiente Necessárias:
```
DOCKER_USERNAME = seu_usuario_dockerhub
DOCKER_PASSWORD = seu_token_dockerhub
```

### O que essa pipeline faz:
- ✅ Faz Checkout do repositório
- ✅ Compila o projeto Maven
- ✅ Executa testes unitários
- ✅ Empacota em JAR
- ✅ Constrói imagem Docker
- ✅ Faz push para Docker Hub (em branch main)

### Triggers Sugeridos:
- Poll SCM: `H/15 * * * *` (a cada 15 minutos)
- Webhook do GitHub

---

## 2️⃣ Pipeline: pipeline_staging

### Configuração no Jenkins:
```
Nome: pipeline_staging
Tipo: Pipeline job
```

### Script Path:
```
Jenkinsfile.staging
```

### Variáveis de Ambiente Necessárias:
```
DOCKER_USERNAME = seu_usuario_dockerhub
DOCKER_PASSWORD = seu_token_dockerhub
```

### O que essa pipeline faz:
- ✅ Faz Checkout do repositório
- ✅ Compila o projeto Maven
- ✅ Executa testes unitários
- ✅ Analisa qualidade de código
- ✅ Empacota em JAR
- ✅ Constrói imagem Docker com tag "staging"
- ✅ Faz push para Docker Hub (em branch main)
- ✅ Simula deploy para Staging

### Triggers Sugeridos:
- Build após outras pipelines: `image_docker`
- Poll SCM: `H/30 * * * *` (a cada 30 minutos)

---

## 3️⃣ Pipeline: pratica_3_docker - pipeline dev

### Configuração no Jenkins:
```
Nome: pratica_3_docker - pipeline dev
Tipo: Pipeline job
```

### Script Path:
```
Jenkinsfile.dev
```

### O que essa pipeline faz:
- ✅ Faz Checkout do repositório
- ✅ Configura ambiente (Java, Maven, Docker)
- ✅ Compila o projeto
- ✅ Executa testes unitários
- ✅ Analisa qualidade de código
- ✅ Empacota em JAR
- ✅ Constrói imagem Docker com tag "dev"
- ✅ Testa a imagem em container
- ✅ Publica resultados dos testes (JUnit)

### Triggers Sugeridos:
- Poll SCM: `H/10 * * * *` (a cada 10 minutos)
- Build quando outras pipelines estiverem estáveis

---

## Configuração de Credenciais no Jenkins

### Passo 1: Gerar Token no Docker Hub
1. Acesse https://hub.docker.com
2. Faça login na sua conta
3. Vá em Account Settings → Security → Personal Access Tokens
4. Clique em "Generate New Token"
5. Copie o token gerado

### Passo 2: Adicionar Credenciais no Jenkins

```
Manage Jenkins → Credentials → System → Global credentials (unrestricted)
Click "Add Credentials"

Kind: Username with password
Username: seu_usuario_dockerhub
Password: seu_token_dockerhub
ID: docker-credentials
Description: Docker Hub Credentials
```

### Passo 3: Usar Credenciais nas Pipelines

Se você quiser usar credenciais de forma segura, atualize os Jenkinsfiles com:

```groovy
environment {
    DOCKER_CREDENTIALS = credentials('docker-credentials')
}

stage('Push to Registry') {
    steps {
        sh '''
            echo "$DOCKER_CREDENTIALS_PSW" | docker login -u "$DOCKER_CREDENTIALS_USR" --password-stdin
            docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
        '''
    }
}
```

---

## Verificação de Arquivo Dockerfile

Se o Dockerfile não existir, as pipelines criarão automaticamente um. 
Para melhor performance, recomenda-se criar um Dockerfile na raiz do projeto:

### Dockerfile Recomendado para Java Spring Boot:

```dockerfile
# Estágio 1: Build
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080
ENV ENVIRONMENT=production
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
```

---

## Troubleshooting

### Erro: "Unable to find Jenkinsfile.staging"
✅ **Resolvido!** Os arquivos foram adicionados ao repositório.

### Erro: "Docker command not found"
- Verifique se Docker está instalado e rodando no agent do Jenkins
- Adicione o usuário do Jenkins ao grupo docker: `sudo usermod -aG docker jenkins`

### Erro: "Failed to push image"
- Verifique credenciais do Docker Hub
- Verifique se o servidor tem acesso à internet
- Verifique se a imagem foi construída com sucesso

### Erro: "Maven command not found"
- Configure a ferramenta Maven no Jenkins: Manage Jenkins → Tools → Maven
- Use o mesmo caminho configurado nos Jenkinsfiles

---

## Monitoramento

Após configurar, você pode:

1. **Visualizar logs em tempo real**
   - Abra a pipeline no Jenkins
   - Clique em "Console Output"

2. **Configurar notificações de falha**
   - Manage Jenkins → Configure System
   - Adicione notificações por email ou Slack

3. **Acompanhar histórico de builds**
   - Cada pipeline mantém histórico dos 10 últimos builds

---

**Status:** ✅ Pronto para usar!

Para mais detalhes, consulte:
- [PIPELINE_SETUP.md](./PIPELINE_SETUP.md)
- [Jenkinsfile](./Jenkinsfile)
- [Jenkinsfile.staging](./Jenkinsfile.staging)
- [Jenkinsfile.docker](./Jenkinsfile.docker)
- [Jenkinsfile.dev](./Jenkinsfile.dev)
