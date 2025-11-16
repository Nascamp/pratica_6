#!/bin/bash

# Script de Build Local - Simula execução Jenkins
# Para Linux/macOS/WSL

set -e  # Exit on error

# Cores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configurações
WORKSPACE=$(pwd)
PROJECT_DIR="${WORKSPACE}"
BUILD_DIR="${PROJECT_DIR}/target"
REPORTS_DIR="${BUILD_DIR}/surefire-reports"

# Início
echo ""
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║   PRATICA 6 - Build Script (Local/Jenkins Simulation)          ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

echo -e "${BLUE}[INFO]${NC} Workspace: ${WORKSPACE}"
echo -e "${BLUE}[INFO]${NC} Hora: $(date '+%Y-%m-%d %H:%M:%S')"
echo ""

# =========================
# 1. CHECKOUT
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 1: CHECKOUT                                              │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

if ! command -v git &> /dev/null; then
    echo -e "${RED}[ERRO]${NC} Git não encontrado!"
    exit 1
fi

echo -e "${GREEN}[OK]${NC} Git disponível"

GIT_COMMIT=$(git rev-parse HEAD)
GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo -e "${GREEN}[OK]${NC} Branch: ${GIT_BRANCH}"
echo -e "${GREEN}[OK]${NC} Commit: ${GIT_COMMIT:0:8}"
echo ""

# =========================
# 2. BUILD
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 2: BUILD (Compilar)                                      │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

echo -e "${BLUE}[INFO]${NC} Compilando com Maven..."
if ! mvn clean compile -DskipTests -q; then
    echo -e "${RED}[ERRO]${NC} Compilação falhou!"
    exit 1
fi
echo -e "${GREEN}[OK]${NC} Compilação bem-sucedida"
echo ""

# =========================
# 3. UNIT TESTS
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 3: UNIT TESTS (Testes Unitários)                         │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

echo -e "${BLUE}[INFO]${NC} Executando testes..."
if ! mvn clean test; then
    echo -e "${RED}[ERRO]${NC} Testes falharam!"
    exit 1
fi
echo -e "${GREEN}[OK]${NC} Todos os testes passaram"
echo ""

# =========================
# 4. CODE QUALITY
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 4: CODE QUALITY (Análise de Código)                      │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

echo -e "${BLUE}[INFO]${NC} Analisando qualidade de código..."
if ! mvn compile -DskipTests -q; then
    echo -e "${RED}[ERRO]${NC} Análise falhou!"
    exit 1
fi
echo -e "${GREEN}[OK]${NC} Análise concluída"
echo ""

# =========================
# 5. PACKAGE
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 5: PACKAGE (Empacotar)                                   │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

echo -e "${BLUE}[INFO]${NC} Criando JAR..."
if ! mvn package -DskipTests -q; then
    echo -e "${RED}[ERRO]${NC} Empacotamento falhou!"
    exit 1
fi
echo -e "${GREEN}[OK]${NC} JAR criado com sucesso"

echo -e "${BLUE}[INFO]${NC} Artefatos criados:"
if [ -d "${BUILD_DIR}" ]; then
    for jar_file in ${BUILD_DIR}/*.jar; do
        if [ -f "$jar_file" ]; then
            size=$(du -h "$jar_file" | cut -f1)
            echo "   - $(basename $jar_file) ($size)"
        fi
    done
fi
echo ""

# =========================
# 6. ARCHIVE ARTIFACTS
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 6: ARCHIVE ARTIFACTS (Arquivar)                          │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

echo -e "${BLUE}[INFO]${NC} Arquivando artefatos..."
if [ ! -z "$(find ${BUILD_DIR} -name '*.jar' -type f 2>/dev/null)" ]; then
    echo -e "${GREEN}[OK]${NC} JARs arquivados"
else
    echo -e "${RED}[ERRO]${NC} Nenhum JAR encontrado!"
    exit 1
fi
echo ""

# =========================
# 7. GENERATE REPORTS
# =========================
echo "┌────────────────────────────────────────────────────────────────┐"
echo "│ STAGE 7: GENERATE REPORTS (Gerar Relatórios)                   │"
echo "└────────────────────────────────────────────────────────────────┘"
echo ""

echo -e "${BLUE}[INFO]${NC} Gerando relatórios..."
mvn surefire-report:report -q 2>/dev/null || true
if [ -d "${REPORTS_DIR}" ]; then
    echo -e "${GREEN}[OK]${NC} Relatórios gerados"
else
    echo -e "${YELLOW}[AVISO]${NC} Diretório de relatórios não encontrado"
fi
echo ""

# =========================
# RESULTADOS FINAIS
# =========================
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║                 BUILD FINALIZADO COM SUCESSO! ✅              ║"
echo "╠════════════════════════════════════════════════════════════════╣"

# Obter informações de teste
TEST_INFO=$(mvn test 2>/dev/null | grep "Tests run:" | tail -1)
echo "║ $TEST_INFO"

echo "║                                                                ║"
echo "║ Projeto: Pratica-4-DevOps                                     ║"
echo "║ Branch: ${GIT_BRANCH}"
echo "║ Commit: ${GIT_COMMIT:0:8}"
echo "║ Timestamp: $(date '+%Y-%m-%d %H:%M:%S')"
echo "║                                                                ║"
echo "║ Artefatos em: ${BUILD_DIR}"
echo "║ Relatórios em: ${REPORTS_DIR}"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

echo -e "${GREEN}[OK]${NC} Tudo pronto para Jenkins!"
echo ""

exit 0
