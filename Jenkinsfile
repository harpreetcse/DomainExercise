pipeline {
  agent any
  environment {
          AWS_ACCOUNT_ID="905418057796"
          AWS_DEFAULT_REGION="us-east-1"
          IMAGE_REPO_NAME="event-store"
          IMAGE_TAG="v1"
          REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
      }
    tools {
      maven 'Maven'
                 jdk 'Java17'
    }
    stages {
        stage('Build maven ') {
            steps {
                    sh 'pwd'
                    sh 'mvn  clean install package'
            }
        }

        stage('Copy Artifact') {
           steps {
                   sh 'pwd'
		   sh 'cp -r target/*.jar docker'
           }
        }

        stage('Logging into AWS ECR') {
                    steps {
                        script {
                        sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                        }

                    }
        }

        // Building Docker images
            stage('Building image') {
              steps{
                script {
                  dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
              }
            }

            // Uploading Docker images into AWS ECR
            stage('Pushing to ECR') {
             steps{
                 script {
                        sh "docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:$IMAGE_TAG"
                        sh "docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                 }
                }
              }

        stage('Build docker image') {
           steps {
               script {
                 def customImage = docker.build('niks29793/eventstore', "./docker")
                 docker.withRegistry('https://registry.hub.docker.com', 'Docker') {
                 customImage.push("${env.BUILD_NUMBER}")
                 }
           }
        }
	  }
    }
}