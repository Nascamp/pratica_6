# 🚀 Como Executar o Build Localmente

Guia rápido para executar o pipeline localmente antes de enviar para Jenkins.

---

## 📋 Scripts Disponíveis

### Windows: `build.bat`
```bash
cd C:\Users\user\OneDrive\Área de Trabalho\pratica_6
build.bat
```

### Linux/macOS/WSL: `build.sh`
```bash
cd ~/sua-pasta/pratica_6
chmod +x build.sh
./build.sh
```

---

## ✅ O que os Scripts Fazem

Os scripts executam automaticamente as seguintes etapas:

1. **CHECKOUT** - Verifica Git (branch e commit)
2. **BUILD** - Compila o código (`mvn clean compile`)
3. **UNIT TESTS** - Roda todos os testes (`mvn clean test`)
4. **CODE QUALITY** - Analisa qualidade (`mvn compile`)
5. **PACKAGE** - Cria JAR (`mvn package`)
6. **ARCHIVE ARTIFACTS** - Verifica artefatos
7. **GENERATE REPORTS** - Gera relatórios

---

## 🎯 Resultado Esperado

Sucesso:
```
╔════════════════════════════════════════════════════════════════╗
║            BUILD FINALIZADO COM SUCESSO! ✅                   ║
╠════════════════════════════════════════════════════════════════╣
║ Tests run: 34, Failures: 0, Errors: 0
║ Projeto: Pratica-4-DevOps
║ Status: OK
╚════════════════════════════════════════════════════════════════╝
```

---

## 🐛 Se Algo Falhar

1. **Leia a mensagem de erro** no console
2. **Corrija o problema** localmente
3. **Re-execute o script**
4. **Depois envie para Jenkins**

### Problemas Comuns

| Problema | Solução |
|----------|---------|
| `mvn: command not found` | Instale Maven ou configure PATH |
| `java: command not found` | Instale Java 17+ ou configure PATH |
| Testes falhando | Execute `mvn clean test` manualmente para debugar |
| Permissão negada (Linux) | Execute `chmod +x build.sh` |

---

## 📊 Verificar Relatórios Localmente

Após execução bem-sucedida:

```
target/
├── Pratica-4-DevOps-0.0.1-SNAPSHOT.jar
├── surefire-reports/
│   ├── index.html ← Abra no navegador
│   └── *.xml
└── test-classes/
```

Para ver os testes:
```bash
# Windows
start target\surefire-reports\index.html

# Linux/macOS
open target/surefire-reports/index.html
# ou
firefox target/surefire-reports/index.html
```

---

## 🚀 Enviar para Jenkins

Após validar localmente com sucesso:

1. **Commit suas mudanças**
   ```bash
   git add .
   git commit -m "Adicionado pipeline Jenkins"
   git push origin main
   ```

2. **Acesse Jenkins**
   ```
   http://seu-jenkins:8080
   ```

3. **Execute a Job**
   - Clique em `Pratica-6-Clean-Architecture`
   - Clique em `Executar Agora`

4. **Monitor o build**
   - Acompanhe em tempo real
   - Veja o console output
   - Verifique os relatórios

---

## 📝 Exemplo de Execução Completa

### Windows
```batch
C:\Users\user\OneDrive\Área de Trabalho\pratica_6> build.bat

╔════════════════════════════════════════════════════════════════╗
║   PRATICA 6 - Build Script (Local/Jenkins Simulation)          ║
╚════════════════════════════════════════════════════════════════╝

[INFO] Workspace: C:\...
[INFO] Hora: 16/11/2025 19:35:00

┌────────────────────────────────────────────────────────────────┐
│ STAGE 1: CHECKOUT                                              │
└────────────────────────────────────────────────────────────────┘

[OK] Git disponível
[OK] Branch: main
[OK] Commit: a1b2c3d4

┌────────────────────────────────────────────────────────────────┐
│ STAGE 2: BUILD (Compilar)                                      │
└────────────────────────────────────────────────────────────────┘

[INFO] Compilando com Maven...
[OK] Compilação bem-sucedida

┌────────────────────────────────────────────────────────────────┐
│ STAGE 3: UNIT TESTS (Testes Unitários)                         │
└────────────────────────────────────────────────────────────────┘

[INFO] Executando testes...
[INFO] Tests run: 34, Failures: 0, Errors: 0, Skipped: 0
[OK] Todos os testes passaram

[... outras etapas ...]

╔════════════════════════════════════════════════════════════════╗
║            BUILD FINALIZADO COM SUCESSO! ✅                    ║
╠════════════════════════════════════════════════════════════════╣
║ Tests run: 34, Failures: 0, Errors: 0
║ Projeto: Pratica-4-DevOps
║ Branch: main
║ Commit: a1b2c3d4
║ Timestamp: 16/11/2025 19:35:30
║ Artefatos em: C:\...\target
║ Relatórios em: C:\...\target\surefire-reports
╚════════════════════════════════════════════════════════════════╝

[OK] Tudo pronto para Jenkins!
```

---

## 🔄 Workflow Completo

```
Local Development
       ↓
   build.bat/sh (Validação Local)
       ↓
   Tudo OK? (100% Sucesso)
       ↓ SIM
   git push
       ↓
   Jenkins Webhook
       ↓
   Jenkinsfile Executado
       ↓
   Email/Slack Notification
       ↓
   Artifacts Archived
```

---

## ⏱️ Tempo Esperado

| Etapa | Tempo |
|-------|-------|
| Checkout | ~1s |
| Build | ~5s |
| Tests | ~3s |
| Package | ~2s |
| Reports | ~2s |
| **Total** | **~13s** |

*Tempos podem variar dependendo da máquina*

---

## ✨ Tips & Tricks

### Apenas Compilar (sem testes)
```bash
mvn clean compile -DskipTests
```

### Apenas Rodar Testes
```bash
mvn clean test
```

### Limpar Tudo
```bash
mvn clean
```

### Modo Offline (Jenkins recomendado)
```bash
mvn -o clean test
```

### Ver logs detalhados
```bash
mvn -X test
```

---

## 🎯 Próximas Ações

Após sucesso local:

1. ✅ Código validado
2. ✅ Testes executados
3. ✅ Artefatos criados
4. ➡️ **Enviar para Jenkins**
5. ➡️ Monitorar CI/CD
6. ➡️ Deploy em produção

---

**Seu pipeline está pronto! 🚀**
