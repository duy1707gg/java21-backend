pipeline {
    agent any

    environment {
        // Giữ lại để log/trace nếu cần, compose sẽ build từ Dockerfile tại repo root
        IMAGE_NAME     = 'java21-backend'
        CONTAINER_NAME = 'java21-backend-container'
        // Dùng docker-compose v1 (docker-compose). Nếu bạn dùng Docker Compose v2, đổi thành: 'docker compose'
        COMPOSE_CMD    = 'docker-compose'
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
                // Maven Wrapper chạy trên Windows agent
                bat '.\\mvnw clean package -DskipTests'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo '🐳 Building & deploying stack with docker-compose...'
                // Tắt và dọn orphan containers nếu có (không fail nếu chưa tồn tại)
                bat """
                %COMPOSE_CMD% down -v --remove-orphans || exit 0
                """
                // Build image từ Dockerfile + chạy cả MySQL và backend ở chế độ detached
                bat """
                %COMPOSE_CMD% up -d --build
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
        always {
            echo '📜 Recent containers status:'
            bat 'docker ps --format "table {{.Names}}\\t{{.Image}}\\t{{.Status}}\\t{{.Ports}}"'
        }
    }
}
