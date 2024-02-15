pipeline {
  agent any
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