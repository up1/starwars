def withRequiredPacts(body) {

    def pactFolders;
    dir('build') {
        git 'file:///Users/alexsoto/git/starwars_pacts'
        pactFolders = findFiles()
    }

    def workspace = pwd()

    echo "*** ${pactFolders.size()}"
    for(int i = 0; i < pactFolders.length; i++) {
        echo ">> ${pactFolders[i].directory} + ${pactFolders[i].path}"
        if (pactFolders[i].directory && pactFolders[i].path.endsWith('_planets_provider')) {
            def pactDirectory = "${workspace}/build/${pactFolders[i].path}"
            withEnv(["pacts=${pactDirectory}"]) {
                println "Pact found at ${pactFolders[i].path}"
                body.call
            }
        }
    }

}

this