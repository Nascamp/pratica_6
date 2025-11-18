pipeline {
    agent any
    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                sh './mvnw.cmd -B -DskipTests=false test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                echo 'Quality gate: expect coverage >= 99% (configured external check)'
            }
        }
        stage('Build Docker Image') {
            when {
                expression { return true /* replace with actual quality-gate condition */ }
            }
            steps {
                sh 'docker build -t pratica4-devops:latest -f Dockerfile .'
            }
        }
    }
}
