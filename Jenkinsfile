pipeline {
    agent any

    environment {
        IMAGE_NAME = 'java21-backend-app'
        CONTAINER_NAME = 'java21-backend-container'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:latest .'
            }
        }

        stage('Deploy Docker Container') {
            steps {
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                    docker run -d --name ${CONTAINER_NAME} -p 5000:5000 ${IMAGE_NAME}:latest
                """
            }
        }
    }
}
