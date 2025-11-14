pipeline {
    agent any

    tools {
        maven "Maven-3.9"
        jdk "JDK17"
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = "Darlananan/pratica6"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Nascamp/pratica_6'
            }
        }

        stage('Testes + Quality Gate') {
            steps {
                sh "mvn clean verify"
            }

            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    pmd canComputeNew: false, pattern: '**/target/pmd.xml'
                }
            }
        }

        stage('Build JAR (sem testes)') {
            steps {
                sh "mvn -DskipTests clean package"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    echo "$DOCKERHUB_CREDENTIALS_PSW" | docker login -u "$DOCKERHUB_CREDENTIALS_USR" --password-stdin
                    docker build -t $IMAGE_NAME:${BUILD_NUMBER} .
                    docker push $IMAGE_NAME:${BUILD_NUMBER}
                '''
            }
        }

        stage('Trigger Staging') {
            steps {
                build job: 'Pipeline_Staging', wait: false, parameters: [
                    string(name: 'IMAGE_TAG', value: "${BUILD_NUMBER}")
                ]
            }
        }

    }
}
