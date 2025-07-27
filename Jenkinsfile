pipeline {
    agent any

    environment {
        IMAGE_NAME = 'java21-backend'
        CONTAINER_NAME = 'java21-backend-container'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Cloning repository...'
                git 'https://github.com/duy1707gg/java21-backend.git'
            }
        }

        stage('Build JAR') {
            steps {
                echo '⚙️ Building Spring Boot JAR...'
                bat './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                bat "docker build -t %IMAGE_NAME%:latest ."
            }
        }

        stage('Deploy Docker Container') {
            steps {
                echo '🚀 Deploying Docker container...'
                bat """
                docker stop %CONTAINER_NAME% || exit 0
                docker rm %CONTAINER_NAME% || exit 0
                docker run -d --name %CONTAINER_NAME% -p 5000:5000 %IMAGE_NAME%:latest
                """
            }
        }
    }

    post {
        success {
            echo '✅ Deployment completed successfully.'
        }
        failure {
            echo '❌ Deployment failed. Please check the logs.'
        }
    }
}
