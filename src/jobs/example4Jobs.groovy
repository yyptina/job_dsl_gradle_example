String basePath = 'example4_all'
String repo = 'yefen123/job_dsl_gradle_example'

folder(basePath) {
    description 'This example shows a way to reuse job definitions for jobs that differ only with a few properties.'
}

[
    [repo: 'repo1', email: 'fen.ye@tietoevry.com'],
    [repo: 'repo2', email: 'fen.ye.ext@ericsson.com'],
    [repo: 'repo3'],
].each { Map config ->

    job("$basePath/ci-${config.repo}") {
        description "Main job for ${config.repo}"
		
		scm {
        github repo
        }

        logRotator {
            numToKeep 5
        }
        triggers {
            scm 'H/5 * * * *'
        }
        steps {
            gradle 'assemble'
        }
        publishers {
            if (config.email) {
                extendedEmail {
                    recipientList config.email
                }
            }
        }
    }
}