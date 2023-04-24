String basePath = 'example3_all'
String repo = 'yefen123/job_dsl_gradle_example'

folder(basePath) {
    description 'This example shows how to use the configure block.'
}

job("$basePath/configure-block-example") {

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

    configure { Node project ->
        project / publishers / 'com.cloudbees.jenkins.GitHubCommitNotifier' {
            resultOnFailure 'FAILURE'
        }
    }
}