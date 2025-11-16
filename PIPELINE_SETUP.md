# Passo a Passo - Subir Repositório no GitHub

## Resumo do Problema
O Jenkins estava falhando em 2 das 3 pipelines porque os Jenkinsfiles específicos não existiam no repositório:
- ❌ `image_docker` - Faltava `Jenkinsfile.docker`
- ❌ `pipeline_staging` - Faltava `Jenkinsfile.staging` 
- ✅ `pratica_3_docker - pipeline dev` - Funcionava

Erro recebido:
```
ERROR: Unable to find Jenkinsfile.staging from git https://github.com/Nascamp/pratica_6.git
```

## Solução Aplicada

### 1. Criação dos Jenkinsfiles Faltantes

Foram criados 3 Jenkinsfiles na raiz do projeto:

#### **Jenkinsfile.staging**
- Pipeline para ambiente de **Staging**
- Inclui: Checkout → Build → Testes → Package → Docker Build → Deploy Staging
- Configurações específicas para ambiente de staging

#### **Jenkinsfile.docker**
- Pipeline exclusivo para **Construção de Imagens Docker**
- Inclui: Build → Testes → Package → Docker Build → Push Registry
- Verifica e cria Dockerfile se não existir

#### **Jenkinsfile.dev**
- Pipeline para ambiente de **Desenvolvimento**
- Inclui: Checkout → Setup → Compile → Testes → Docker Build → Testes em Container
- Publica resultados dos testes

### 2. Comandos Git Executados

**Passo 1: Verificar Status**
```bash
cd "C:\Users\user\OneDrive\Área de Trabalho\pratica_6"
git status
```
Resultado: 3 arquivos não rastreados

**Passo 2: Adicionar Arquivos ao Staging**
```bash
git add Jenkinsfile.staging Jenkinsfile.docker Jenkinsfile.dev
```

**Passo 3: Fazer Commit**
```bash
git commit -m "feat: adicionar Jenkinsfiles para pipelines de staging, docker e dev

- Jenkinsfile.staging: Pipeline para ambiente de staging com Docker
- Jenkinsfile.docker: Pipeline para construção e push de imagens Docker
- Jenkinsfile.dev: Pipeline para desenvolvimento com testes e Docker

Esses arquivos foram criados para resolver os erros de pipeline no Jenkins."
```

**Passo 4: Fazer Push para GitHub**
```bash
git push origin main
```

### 3. Resultado Final

✅ **Sucesso!** Os arquivos foram enviados para o repositório:
```
To https://github.com/Nascamp/pratica_6.git
   fd1ca96..3cb0876  main -> main
```

**Commit Hash:** `3cb0876`

## Próximos Passos no Jenkins

1. ✅ Agora o Jenkins conseguirá encontrar os Jenkinsfiles
2. Execute os pipelines novamente:
   - `image_docker` (usará `Jenkinsfile.docker`)
   - `pipeline_staging` (usará `Jenkinsfile.staging`)
   - `pratica_3_docker - pipeline dev` (usará `Jenkinsfile.dev`)
3. Configure as credenciais do Docker Registry se necessário:
   - `DOCKER_USERNAME`
   - `DOCKER_PASSWORD`

## Configuração de Credenciais no Jenkins

Se o push Docker falhar, configure as credenciais:

1. Acesse Jenkins → Manage Jenkins → Credentials
2. Adicione novas credenciais com as variáveis:
   - `DOCKER_USERNAME`: seu usuário Docker Hub
   - `DOCKER_PASSWORD`: seu token/senha Docker Hub

## Arquivos Criados

```
pratica_6/
├── Jenkinsfile                 (Principal - já existia)
├── Jenkinsfile.staging         ✅ NOVO
├── Jenkinsfile.docker          ✅ NOVO
├── Jenkinsfile.dev             ✅ NOVO
└── ... (outros arquivos)
```

## Referência do Repositório

- **URL:** https://github.com/Nascamp/pratica_6.git
- **Branch:** main
- **Último Commit:** feat: adicionar Jenkinsfiles para pipelines

---

**Status:** ✅ Repositório atualizado e pronto para pipelines Jenkins
