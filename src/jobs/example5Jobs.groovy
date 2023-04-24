String basePath = 'example5_all'
String repo = 'yefen123/job_dsl_gradle_example'

folder(basePath) {
    description 'This example shows basic email notification.'
}

job("$basePath/gradle-example-build") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        gradle 'assemble'
    }
	publishers {
        extendedEmail {
            recipientList('fen.ye@tietoevry.com')
            triggers {
                success {
                    attachBuildLog(true)
                    subject('build success.')
                    content('Body')
                    sendTo {
                        recipientList()
                    }
                }
            }
        }
    }
}
