pipeline{
    agent any

    environment{
        NETLIFY_SITE_ID = "29700102-44df-434a-a52a-95d29a490295"
    }

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
                    node_modules/.bin/netlify --version
                    echo "Deploying to production. Site Id $"29700102-44df-434a-a52a-95d29a490295"
                    
                    '''
                }
            }

        }
        post {
            always {
                junit 'test-results/junit.xml'
            }
        }
        stage('Deploy') {
            agent{
                docker{
                    image 'node: 18-alpine'
                    reuseNode true
                }
            }
            steps{
                sh '''
                npm install netlify-cli
                node_modules/.bin/netlify --version

                '''
    }
}