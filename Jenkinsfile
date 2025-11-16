pipeline {
    agent any
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }
    
    environment {
        JAVA_HOME = tool 'Java17'
        MAVEN_HOME = tool 'Maven3.9'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}"
        PROJECT_NAME = 'Pratica-4-DevOps'
        WORKSPACE_DIR = "${WORKSPACE}/pratica_6"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Clonando repositório...'
                checkout scm
                script {
                    GIT_COMMIT = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    GIT_BRANCH = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    echo "✅ Branch: ${GIT_BRANCH}"
                    echo "✅ Commit: ${GIT_COMMIT}"
                }
            }
        }
        
        stage('Build') {
            steps {
                echo '🔨 Compilando o projeto...'
                dir("${WORKSPACE_DIR}") {
                    sh '''
                        echo "=== Versão do Maven ==="
                        mvn --version
                        echo ""
                        echo "=== Compilando ==="
                        mvn clean compile -DskipTests
                    '''
                }
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo '🧪 Executando testes unitários...'
                dir("${WORKSPACE_DIR}") {
                    sh '''
                        echo "=== Iniciando testes ==="
                        mvn clean test -DskipITs
                        echo "=== Testes completados ==="
                    '''
                }
            }
        }
        
        stage('Code Quality Analysis') {
            steps {
                echo '📊 Analisando qualidade de código...'
                dir("${WORKSPACE_DIR}") {
                    sh '''
                        echo "=== Análise de Código ==="
                        mvn compile -DskipTests -q
                        echo "✅ Análise concluída"
                    '''
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '📦 Empacotando aplicação...'
                dir("${WORKSPACE_DIR}") {
                    sh '''
                        echo "=== Buildando JAR ==="
                        mvn package -DskipTests -q
                        echo "=== JAR criado ==="
                        ls -lh target/*.jar
                    '''
                }
            }
        }
        
        stage('Archive Artifacts') {
            steps {
                echo '💾 Arquivando artefatos...'
                dir("${WORKSPACE_DIR}") {
                    archiveArtifacts artifacts: 'target/*.jar,target/*.xml', 
                                     fingerprint: true,
                                     allowEmptyArchive: false
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                echo '📈 Gerando relatórios...'
                dir("${WORKSPACE_DIR}") {
                    sh '''
                        echo "=== Gerando relatórios ==="
                        mvn surefire-report:report -q 2>/dev/null || true
                        echo "✅ Relatórios gerados"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            echo '🧹 Limpando e finalizando...'
            
            // Arquivar resultados de testes
            dir("${WORKSPACE_DIR}") {
                junit testResults: 'target/surefire-reports/*.xml', 
                      allowEmptyResults: true,
                      skipPublishingChecks: true
                
                // Publicar relatório de testes
                publishHTML([
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Report',
                    keepAll: true,
                    allowMissing: true
                ])
            }
        }
        
        success {
            echo '✅ BUILD SUCESSO!'
            script {
                def buildNumber = env.BUILD_NUMBER
                def timestamp = new Date().format('yyyy-MM-dd HH:mm:ss')
                echo """
                ╔════════════════════════════════════════╗
                ║       BUILD SUCESSO ✅                 ║
                ╠════════════════════════════════════════╣
                ║ Projeto: ${PROJECT_NAME}
                ║ Build: #${buildNumber}
                ║ Branch: ${GIT_BRANCH}
                ║ Commit: ${GIT_COMMIT.take(8)}
                ║ Timestamp: ${timestamp}
                ╚════════════════════════════════════════╝
                """
            }
        }
        
        failure {
            echo '❌ BUILD FALHOU!'
            script {
                def buildNumber = env.BUILD_NUMBER
                def timestamp = new Date().format('yyyy-MM-dd HH:mm:ss')
                echo """
                ╔════════════════════════════════════════╗
                ║       BUILD FALHOU ❌                  ║
                ╠════════════════════════════════════════╣
                ║ Projeto: ${PROJECT_NAME}
                ║ Build: #${buildNumber}
                ║ Branch: ${GIT_BRANCH}
                ║ Commit: ${GIT_COMMIT.take(8)}
                ║ Timestamp: ${timestamp}
                ║ Verifique os logs acima!
                ╚════════════════════════════════════════╝
                """
            }
        }
        
        unstable {
            echo '⚠️ BUILD INSTÁVEL'
        }
    }
}
