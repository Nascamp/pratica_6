# 🚀 Passo a Passo - Como Subir Repositório no GitHub via Git Bash

## Prerequisitos
- ✅ Git instalado ([Download aqui](https://git-scm.com/download/win))
- ✅ Conta no GitHub
- ✅ Projeto local já inicializado com Git
- ✅ Acesso ao repositório (push permissionado)

---

## 📝 Passo 1: Abrir Git Bash

No seu projeto (`C:\Users\user\OneDrive\Área de Trabalho\pratica_6`):

1. **Clique com botão direito na pasta**
2. **Selecione: "Git Bash Here"**

Ou abra o Git Bash e execute:
```bash
cd "C:\Users\user\OneDrive\Área de Trabalho\pratica_6"
```

---

## ✔️ Passo 2: Verificar Status do Repositório

```bash
git status
```

**Resposta esperada:**
```
On branch main
Your branch is up to date with 'origin/main'.

Untracked files:
  (use "git add <file>..." to include in commit)
        arquivo1.txt
        arquivo2.txt
        
nothing added to commit but untracked files present
```

---

## ➕ Passo 3: Adicionar Arquivos ao Stage (Preparar para Commit)

### Opção A: Adicionar arquivo específico
```bash
git add Jenkinsfile.staging
```

### Opção B: Adicionar múltiplos arquivos
```bash
git add Jenkinsfile.staging Jenkinsfile.docker Jenkinsfile.dev
```

### Opção C: Adicionar todos os arquivos
```bash
git add .
```

**Verificar status após adicionar:**
```bash
git status
```

**Resposta esperada:**
```
On branch main

Changes to be committed:
  (use "git restore --staged <file>..." to unstage)
        new file:   Jenkinsfile.staging
        new file:   Jenkinsfile.docker
        new file:   Jenkinsfile.dev
```

---

## 💬 Passo 4: Fazer Commit (Registrar Mudanças)

```bash
git commit -m "feat: adicionar Jenkinsfiles para pipelines"
```

### Commit com mensagem descritiva (recomendado):
```bash
git commit -m "feat: adicionar Jenkinsfiles para pipelines de staging, docker e dev

- Jenkinsfile.staging: Pipeline para ambiente de staging
- Jenkinsfile.docker: Pipeline para construção de imagens Docker  
- Jenkinsfile.dev: Pipeline para desenvolvimento

Resolve erro de pipeline no Jenkins."
```

**Resposta esperada:**
```
[main 3cb0876] feat: adicionar Jenkinsfiles para pipelines
 3 files changed, 533 insertions(+)
 create mode 100644 Jenkinsfile.staging
 create mode 100644 Jenkinsfile.docker
 create mode 100644 Jenkinsfile.dev
```

---

## 🚀 Passo 5: Fazer Push para GitHub

```bash
git push origin main
```

**Resposta esperada:**
```
Enumerating objects: 6, done.
Counting objects: 100% (6/6), done.
Delta compression: 100% (5/5), done.
Writing objects: 100% (5/5), 3.59 KiB | 3.59 MiB/s, done.

To https://github.com/Nascamp/pratica_6.git
   fd1ca96..3cb0876  main -> main
```

✅ **Sucesso!** Os arquivos foram enviados ao GitHub!

---

## 🔍 Passo 6: Verificar Resultado

### Comando 1: Ver commit local
```bash
git log --oneline -3
```

**Resposta:**
```
3cb0876 (HEAD -> main, origin/main) feat: adicionar Jenkinsfiles
fd1ca96 arquivos jenkins
b3bf91d refatoração do código
```

### Comando 2: Ver branch remoto
```bash
git branch -v
```

**Resposta:**
```
* main fd1ca96 feat: adicionar Jenkinsfiles
```

### Comando 3: Ver origin remoto
```bash
git remote -v
```

**Resposta:**
```
origin  https://github.com/Nascamp/pratica_6.git (fetch)
origin  https://github.com/Nascamp/pratica_6.git (push)
```

---

## 📊 Resumo dos Comandos (Rápido)

Se você quer fazer tudo de uma vez:

```bash
# 1. Entrar na pasta
cd "C:\Users\user\OneDrive\Área de Trabalho\pratica_6"

# 2. Verificar status
git status

# 3. Adicionar arquivos
git add .

# 4. Fazer commit
git commit -m "feat: adicionar arquivos"

# 5. Fazer push
git push origin main

# 6. Verificar resultado
git log --oneline -3
```

---

## ⚙️ Configuração Inicial (Se for primeira vez)

Se é a primeira vez usando Git, configure:

```bash
# Configurar seu nome
git config --global user.name "Seu Nome"

# Configurar seu email
git config --global user.email "seu.email@example.com"

# Verificar configuração
git config --list
```

---

## 🔐 Autenticação no GitHub

### Opção 1: HTTPS (Token de Acesso Pessoal)
```bash
git push origin main
# Será pedido username e password/token
```

1. No GitHub, vá em: Settings → Developer Settings → Personal Access Tokens
2. Clique em "Tokens (classic)"
3. Gere um novo token com permissão `repo`
4. Use como password no Git Bash

### Opção 2: SSH (Recomendado)
```bash
# Gerar chave SSH
ssh-keygen -t ed25519 -C "seu.email@example.com"

# Copiar chave pública
cat ~/.ssh/id_ed25519.pub

# No GitHub: Settings → SSH and GPG keys → New SSH key
# Cole a chave pública

# Testar conexão
ssh -T git@github.com
```

Após configurar SSH, use:
```bash
git push origin main
# Sem pedir autenticação
```

---

## ❌ Troubleshooting

### Erro: "fatal: unable to access 'https://github.com/...': could not resolve host"
```bash
# Verifique conexão com internet
# Ou tente com SSH em vez de HTTPS
```

### Erro: "fatal: 'origin' does not appear to be a 'git' repository"
```bash
# Você não está no diretório correto
cd "C:\Users\user\OneDrive\Área de Trabalho\pratica_6"
git remote -v  # Verificar
```

### Erro: "fatal: authentication failed"
```bash
# Suas credenciais estão erradas
# Reconfigure token no GitHub ou chave SSH
```

### Erro: "Permission denied"
```bash
# Você não tem acesso ao repositório
# Verifique se é colaborador do projeto
# Ou use credenciais corretas
```

---

## 📚 Comandos Úteis Adicionais

### Ver commits
```bash
git log --oneline      # Última linha dos commits
git log --graph        # Árvore de commits
git log -p             # Mostrar mudanças de cada commit
```

### Ver diferenças
```bash
git diff               # Mudanças não staged
git diff --staged      # Mudanças staged
```

### Desfazer ações
```bash
git restore arquivo.txt                    # Descartar mudanças locais
git restore --staged arquivo.txt           # Remover do stage
git reset HEAD~1                           # Desfazer último commit
git revert HEAD                            # Criar commit que desfaz anterior
```

### Branches
```bash
git branch                    # Listar branches locais
git branch -r                 # Listar branches remotos
git branch novofeature        # Criar novo branch
git checkout novofeature      # Mudar para branch
git checkout -b novofeature   # Criar e mudar para novo branch
```

### Pull (Trazer mudanças remotas)
```bash
git pull origin main     # Trazer e fazer merge
git fetch origin main    # Apenas trazer sem merge
```

---

## ✨ Boas Práticas

1. **Sempre fazer `git pull` antes de `git push`:**
   ```bash
   git pull origin main
   git push origin main
   ```

2. **Usar mensagens de commit claras:**
   ```bash
   ❌ git commit -m "fix"
   ✅ git commit -m "fix: corrigir erro de autenticação no login"
   ```

3. **Fazer commits pequenos e frequentes:**
   - Cada commit deve representar uma mudança lógica
   - Mais fácil de revisar e reverter se necessário

4. **Revisar mudanças antes de fazer commit:**
   ```bash
   git diff
   git status
   ```

5. **Usar `.gitignore` para arquivos que não devem ir:**
   ```bash
   # Ver/editar .gitignore
   cat .gitignore
   echo "target/" >> .gitignore
   ```

---

## 🎯 Fluxo Completo Recomendado

```bash
# 1. Clonar repositório (primeira vez)
git clone https://github.com/Nascamp/pratica_6.git
cd pratica_6

# 2. Criar seu branch (opcional)
git checkout -b minha-feature

# 3. Fazer mudanças nos arquivos
# ... editar arquivos ...

# 4. Verificar mudanças
git status
git diff

# 5. Adicionar mudanças
git add .

# 6. Fazer commit
git commit -m "feat: descrição das mudanças"

# 7. Trazer mudanças remotas
git pull origin main

# 8. Fazer push
git push origin main

# 9. Verificar resultado
git log --oneline -3
```

---

**🎉 Pronto! Agora você sabe como subir repositórios no GitHub via Git Bash!**

Para mais informações: https://git-scm.com/doc
