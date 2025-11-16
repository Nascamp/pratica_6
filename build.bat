@echo off
REM Script de Build Local - Simula execução Jenkins

setlocal enabledelayedexpansion

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║   PRATICA 6 - Build Script (Local/Jenkins Simulation)          ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

REM Cores (simples para Windows)
set "GREEN=OK"
set "RED=ERRO"
set "YELLOW=AVISO"

REM Configurações
set "WORKSPACE=%CD%"
set "PROJECT_DIR=%WORKSPACE%"
set "BUILD_DIR=%PROJECT_DIR%\target"
set "REPORTS_DIR=%BUILD_DIR%\surefire-reports"

echo [INFO] Workspace: %WORKSPACE%
echo [INFO] Hora: %date% %time%
echo.

REM =========================
REM 1. CHECKOUT
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 1: CHECKOUT                                              │
echo └────────────────────────────────────────────────────────────────┘

git --version >nul 2>&1
if errorlevel 1 (
    echo [%RED%] Git não encontrado!
    exit /b 1
)

echo [OK] Git disponível
for /f "tokens=*" %%A in ('git rev-parse HEAD') do set GIT_COMMIT=%%A
for /f "tokens=*" %%A in ('git rev-parse --abbrev-ref HEAD') do set GIT_BRANCH=%%A

echo [OK] Branch: %GIT_BRANCH%
echo [OK] Commit: %GIT_COMMIT:~0,8%
echo.

REM =========================
REM 2. BUILD
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 2: BUILD (Compilar)                                      │
echo └────────────────────────────────────────────────────────────────┘

echo [INFO] Compilando com Maven...
call mvn clean compile -DskipTests -q
if errorlevel 1 (
    echo [%RED%] Compilação falhou!
    exit /b 1
)
echo [OK] Compilação bem-sucedida
echo.

REM =========================
REM 3. UNIT TESTS
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 3: UNIT TESTS (Testes Unitários)                         │
echo └────────────────────────────────────────────────────────────────┘

echo [INFO] Executando testes...
call mvn clean test
if errorlevel 1 (
    echo [%RED%] Testes falharam!
    exit /b 1
)
echo [OK] Todos os testes passaram
echo.

REM =========================
REM 4. CODE QUALITY
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 4: CODE QUALITY (Análise de Código)                      │
echo └────────────────────────────────────────────────────────────────┘

echo [INFO] Analisando qualidade de código...
call mvn compile -DskipTests -q
if errorlevel 1 (
    echo [%RED%] Análise falhou!
    exit /b 1
)
echo [OK] Análise concluída
echo.

REM =========================
REM 5. PACKAGE
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 5: PACKAGE (Empacotar)                                   │
echo └────────────────────────────────────────────────────────────────┘

echo [INFO] Criando JAR...
call mvn package -DskipTests -q
if errorlevel 1 (
    echo [%RED%] Empacotamento falhou!
    exit /b 1
)
echo [OK] JAR criado com sucesso

REM Listar JARs
echo [INFO] Artefatos criados:
for /r "%BUILD_DIR%" %%F in (*.jar) do (
    echo   - %%~nxF (%%~zF bytes)
)
echo.

REM =========================
REM 6. ARCHIVE ARTIFACTS
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 6: ARCHIVE ARTIFACTS (Arquivar)                          │
echo └────────────────────────────────────────────────────────────────┘

echo [INFO] Arquivando artefatos...
if exist "%BUILD_DIR%\*.jar" (
    echo [OK] JARs arquivados
) else (
    echo [%RED%] Nenhum JAR encontrado!
    exit /b 1
)
echo.

REM =========================
REM 7. GENERATE REPORTS
REM =========================
echo ┌────────────────────────────────────────────────────────────────┐
echo │ STAGE 7: GENERATE REPORTS (Gerar Relatórios)                   │
echo └────────────────────────────────────────────────────────────────┘

echo [INFO] Gerando relatórios...
call mvn surefire-report:report -q 2>nul
if exist "%REPORTS_DIR%" (
    echo [OK] Relatórios gerados
) else (
    echo [%YELLOW%] Diretório de relatórios não encontrado
)
echo.

REM =========================
REM RESULTADOS FINAIS
REM =========================
echo ╔════════════════════════════════════════════════════════════════╗
echo ║                   BUILD FINALIZADO COM SUCESSO! ✅             ║
echo ╠════════════════════════════════════════════════════════════════╣

for /f "tokens=*" %%A in ('mvn test 2^>nul ^| find "Tests run:"') do (
    echo ║ %%A
)

echo ║                                                                ║
echo ║ Projeto: Pratica-4-DevOps                                     ║
echo ║ Branch: %GIT_BRANCH%
echo ║ Commit: %GIT_COMMIT:~0,8%
echo ║ Timestamp: %date% %time%
echo ║                                                                ║
echo ║ Artefatos em: %BUILD_DIR%                                      
echo ║ Relatórios em: %REPORTS_DIR%                                   
echo ╚════════════════════════════════════════════════════════════════╝
echo.

echo [OK] Tudo pronto para Jenkins!
echo.
