pipeline{
    agent any

    stages{
        stage('Deploy') {
            agent{
                docker{
                    image 'node: 18-alpine'
                    reuseNode true
                }
            }
            steps{
                sh '''
                ls -la
                node  --version
                npm  --version
                npm ci
                npm run build

                '''
            }
        }
        stage('Test') {
            step {
                docker {
                    image 'node:18-alphine'
                    reuseNode true
                }
                steps {

                sh '''
                    npm install netlify-cli 
                    netlify --version
                    
                    '''
                }
            }

        }
        post {
            always {
                junit 'test-results/junit.xml'
            }
        }
        stage('Build') {
            agent{
                docker{
                    image 'node: 18-alpine'
                    reuseNode true
                }
            }
            steps{
                sh '''
                ls -la
                node  --version
                npm  --version
                npm ci
                npm run build
    }
}