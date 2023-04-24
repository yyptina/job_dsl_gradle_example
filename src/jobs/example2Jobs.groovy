import groovy.json.JsonSlurper

String basePath = 'example2_all'
String repo = 'yefen123/jobdsl_jobs_test'

folder(basePath) {
    description 'This example shows how to create a set of jobs for each github branch, each in its own folder.'
}

URL branchUrl = "https://api.github.com/repos/$repo/branches".toURL()
List branches = new JsonSlurper().parse(branchUrl.newReader())
branches.each { branch ->

    String safeBranchName = branch.name.replaceAll('/', '-')

    folder "$basePath/$safeBranchName"

    job("$basePath/$safeBranchName/gradle-example-build") {
        scm {
            github repo, branch.name
        }
        triggers {
            scm 'H/30 * * * *'
        }
        steps {
            gradle 'assemble'
        }
    }

    job("$basePath/$safeBranchName/gradle-example-deploy") {
        parameters {
            stringParam 'host'
        }
        steps {
            shell 'scp war file; restart...'
        }
    }
}