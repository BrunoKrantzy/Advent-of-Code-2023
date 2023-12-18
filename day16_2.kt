
// à réécrire proprement en fonctions regroupant les doublons de bloc
// écriture pourrie mais fonctionne parfaitement

fun main() {
    val inLines: List<String>

    val nbLig:Int
    val nbCol:Int

    val DebL = 0
    val FinL:Int
    val DebC = 0
    val FinC:Int

    val bTest = false
    if (bTest) {
        inLines = readInput("test16_1")
        nbLig = 10
        nbCol = 10
        FinL = 9
        FinC = 9
    }
    else {
        inLines = readInput("input16_1")
        nbLig = 110
        nbCol = 110
        FinL = 109
        FinC = 109
    }

    data class Visited(val posCur:Pair<Int, Int>, val dir:Char)

    var tabCircuit = Array(nbLig) { Array<Char>(nbCol) { '@'} }
    var tabEnergie = Array(nbLig) { Array<Char>(nbCol) { '.'} }

    var lstVisited = mutableListOf<Visited>()

    var setVisited = mutableSetOf<Visited>()
    var lstFlux = ArrayList<MutableList<Pair<Int,Int>>>()
    var tabDirFlux = mutableListOf<Char>()

    inLines.forEachIndexed { ixL, str ->
        str.forEachIndexed { ixC, car ->
            tabCircuit[ixL][ixC] = car
        }
    }

    var fluxActif = 0
    var posCurseur = Pair(0, 0)
    var carLu = ' '

    fun cleanTabEnergie() {
        for (l in 0..FinL) {
            for (c in 0..FinC) {
                tabEnergie[l][c] = '.'
            }
        }
    }

    fun cleanVar() {
        lstFlux.clear()
        tabDirFlux.clear()
        cleanTabEnergie()
        setVisited.clear()
        lstVisited.clear()
    }

    fun calculEnergie() : Int {
        var nrj = 0
        tabEnergie.forEach { strL ->
            strL.forEach { car ->
                if (car != '.') nrj++
            }
        }
        return nrj
    }

    fun traiteCarLu(carL:Char, dir:Char, curIn:Pair<Int, Int>) : Pair<Int, Int> {
        var posCur = Pair(0, 0)

        when (carLu) {
            '/' -> {
                tabEnergie[curIn.first][curIn.second] = '/'
                when (dir) {
                    'E' -> {
                        tabDirFlux[fluxActif] = 'N'
                        if (!setVisited.contains(Visited(curIn, 'E'))) {
                            setVisited.add(Visited(curIn, 'E'))
                            lstVisited.add(Visited(curIn, 'E'))
                        }
                        if (curIn.first > DebL) {
                            posCur = Pair(curIn.first - 1, curIn.second)
                        }
                    }
                    'W' -> {
                        tabDirFlux[fluxActif] = 'S'
                        if (!setVisited.contains(Visited(curIn, 'W'))) {
                            setVisited.add(Visited(curIn, 'W'))
                            lstVisited.add(Visited(curIn, 'W'))
                        }
                        if (curIn.first < FinL) {
                            posCur = Pair(curIn.first + 1, curIn.second)
                        }
                    }
                    'N' -> {
                        tabDirFlux[fluxActif] = 'E'
                        if (!setVisited.contains(Visited(curIn, 'N'))) {
                            setVisited.add(Visited(curIn, 'N'))
                            lstVisited.add(Visited(curIn, 'N'))
                        }
                        if (curIn.second < FinC) {
                            posCur = Pair(curIn.first, curIn.second + 1)
                        }
                    }
                    'S' -> {
                        tabDirFlux[fluxActif] = 'W'
                        if (!setVisited.contains(Visited(curIn, 'S'))) {
                            setVisited.add(Visited(curIn, 'S'))
                            lstVisited.add(Visited(curIn, 'S'))
                        }
                        if (curIn.second > DebC) {
                            posCur = Pair(curIn.first, curIn.second - 1)
                        }
                    }
                }
            }

            '\\' -> {
                tabEnergie[curIn.first][curIn.second] = '\\'
                when (dir) {
                    'E' -> {
                        tabDirFlux[fluxActif] = 'S'
                        if (!setVisited.contains(Visited(curIn, 'E'))) {
                            setVisited.add(Visited(curIn, 'E'))
                            lstVisited.add(Visited(curIn, 'E'))
                        }
                        if (curIn.first < FinL) {
                            posCur = Pair(curIn.first + 1, curIn.second)
                        }
                    }
                    'W' -> {
                        tabDirFlux[fluxActif] = 'N'
                        if (!setVisited.contains(Visited(curIn, 'W'))) {
                            setVisited.add(Visited(curIn, 'W'))
                            lstVisited.add(Visited(curIn, 'W'))
                        }
                        if (curIn.first > DebL) {
                            posCur = Pair(curIn.first - 1, curIn.second)
                        }
                    }
                    'S' -> {
                        tabDirFlux[fluxActif] = 'E'
                        if (!setVisited.contains(Visited(curIn, 'S'))) {
                            setVisited.add(Visited(curIn, 'S'))
                            lstVisited.add(Visited(curIn, 'S'))
                        }
                        if (curIn.second < FinC) {
                            posCur = Pair(curIn.first, curIn.second + 1)
                        }
                    }
                    'N' -> {
                        tabDirFlux[fluxActif] = 'W'
                        if (!setVisited.contains(Visited(curIn, 'N'))) {
                            setVisited.add(Visited(curIn, 'N'))
                            lstVisited.add(Visited(curIn, 'N'))
                        }
                        if (curIn.second > DebC) {
                            posCur = Pair(curIn.first, curIn.second - 1)
                        }
                    }
                }
            }

            '|' -> {
                tabEnergie[curIn.first][curIn.second] = '|'
                when (dir) {
                    'E' -> {
                        if (curIn.first == DebL) { // on va au Sud
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'E'))) {
                                posCur = Pair(curIn.first + 1, curIn.second)
                                tabDirFlux[fluxActif] = 'S'
                            }
                        }
                        else if (curIn.first == FinL) { // on va au Nord
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'E'))) {
                                posCur = Pair(curIn.first - 1, curIn.second)
                                tabDirFlux[fluxActif] = 'N'
                            }
                        }
                        // ligne standard
                        else if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'E'))) {
                            posCur = Pair(curIn.first - 1, curIn.second)
                            tabDirFlux[fluxActif] = 'N' // Nord par défaut
                            // et flux vers le Sud
                            lstFlux.add(mutableListOf())
                            val posC = Pair(curIn.first + 1, curIn.second)
                            lstFlux[lstFlux.size - 1].add(posC)
                            tabDirFlux.add('S')
                        }
                        setVisited.add(Visited(Pair(curIn.first, curIn.second), 'E'))
                        lstVisited.add(Visited(curIn, 'E'))
                    }
                    'W' -> {
                        if (curIn.first == DebL) { // on va au Sud
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'W'))) {
                                posCur = Pair(curIn.first + 1, curIn.second)
                                tabDirFlux[fluxActif] = 'S'
                            }
                        }
                        else if (curIn.first == FinL) { // on va au Nord
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'W'))) {
                                posCur = Pair(curIn.first - 1, curIn.second)
                                tabDirFlux[fluxActif] = 'N'
                            }
                        }
                        // ligne standard
                        else if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'W'))) {
                            posCur = Pair(curIn.first - 1, curIn.second)
                            tabDirFlux[fluxActif] = 'N' // Nord par défaut
                            // et flux vers le Sud
                            lstFlux.add(mutableListOf())
                            val posC = Pair(curIn.first + 1, curIn.second)
                            lstFlux[lstFlux.size - 1].add(posC)
                            tabDirFlux.add('S')
                        }
                        setVisited.add(Visited(Pair(curIn.first, curIn.second), 'W'))
                        lstVisited.add(Visited(curIn, 'W'))
                    }
                    'N' -> {
                        tabDirFlux[fluxActif] = 'N'
                        if (!setVisited.contains(Visited(curIn, 'N'))) {
                            setVisited.add(Visited(curIn, 'N'))
                            lstVisited.add(Visited(curIn, 'N'))
                        }
                        if (curIn.first > DebL) {
                            posCur = Pair(curIn.first - 1, curIn.second)
                        }
                    }
                    'S' -> {
                        tabDirFlux[fluxActif] = 'S'
                        if (!setVisited.contains(Visited(curIn, 'S'))) {
                            setVisited.add(Visited(curIn, 'S'))
                            lstVisited.add(Visited(curIn, 'S'))
                        }
                        if (curIn.first < FinL) {
                            posCur = Pair(curIn.first + 1, curIn.second)
                        }
                    }
                }
            }

            '-' -> {
                tabEnergie[curIn.first][curIn.second] = '-'
                when (dir) {
                    'N' -> {
                        if (curIn.second == DebC) { // on va à Est
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'N'))) {
                                posCur = Pair(curIn.first, curIn.second + 1)
                                tabDirFlux[fluxActif] = 'E'
                            }
                        }
                        else if (curIn.second == FinC) { // on va à Ouest
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'N'))) {
                                posCur = Pair(curIn.first, curIn.second - 1)
                                tabDirFlux[fluxActif] = 'W'
                            }
                        }
                        // ligne standard
                        else if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'N'))) {
                            posCur = Pair(curIn.first, curIn.second - 1)
                            tabDirFlux[fluxActif] = 'W' // Ouest par défaut
                            // et flux vers Est
                            lstFlux.add(mutableListOf())
                            val posC = Pair(curIn.first, curIn.second + 1)
                            lstFlux[lstFlux.size - 1].add(posC)
                            tabDirFlux.add('E')
                        }
                        setVisited.add(Visited(Pair(curIn.first, curIn.second), 'N'))
                        lstVisited.add(Visited(curIn, 'N'))
                    }
                    'S' -> {
                        if (curIn.second == DebC) { // on va à Est
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'S'))) {
                                posCur = Pair(curIn.first, curIn.second + 1)
                                tabDirFlux[fluxActif] = 'E'
                            }
                        }
                        else if (curIn.second == FinC) { // on va à Ouest
                            if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'S'))) {
                                posCur = Pair(curIn.first, curIn.second - 1)
                                tabDirFlux[fluxActif] = 'W'
                            }
                        }
                        // ligne standard
                        else if (!setVisited.contains(Visited(Pair(curIn.first, curIn.second), 'S'))) {
                            posCur = Pair(curIn.first, curIn.second - 1)
                            tabDirFlux[fluxActif] = 'W' // Ouest par défaut
                            // et flux vers Est
                            lstFlux.add(mutableListOf())
                            val posC = Pair(curIn.first, curIn.second + 1)
                            lstFlux[lstFlux.size - 1].add(posC)
                            tabDirFlux.add('E')
                        }
                        setVisited.add(Visited(Pair(curIn.first, curIn.second), 'S'))
                        lstVisited.add(Visited(curIn, 'S'))
                    }
                    'E' -> {
                        if (!setVisited.contains(Visited(curIn, 'E'))) {
                            setVisited.add(Visited(curIn, 'E'))
                            lstVisited.add(Visited(curIn, 'E'))
                        }
                        if (curIn.second < FinC) {
                            posCur = Pair(curIn.first, curIn.second + 1)
                        }
                    }
                    'W' -> {
                        if (!setVisited.contains(Visited(curIn, 'W'))) {
                            setVisited.add(Visited(curIn, 'W'))
                            lstVisited.add(Visited(curIn, 'W'))
                        }
                        if (curIn.second > DebC) {
                            posCur = Pair(curIn.first, curIn.second - 1)
                        }
                    }
                }
            }

            '.' -> {
                tabEnergie[curIn.first][curIn.second] = '#'
                when (dir) {
                    'N' -> {
                        if (curIn.first > DebL) {
                            posCur = Pair(curIn.first - 1, curIn.second)
                        }
                    }
                    'S' -> {
                        if (curIn.first < FinL) {
                            posCur = Pair(curIn.first + 1, curIn.second)
                        }
                    }
                    'E' -> {
                        if (curIn.second < FinC) {
                            posCur = Pair(curIn.first, curIn.second + 1)
                        }
                    }
                    'W' -> {
                        if (curIn.second > DebC) {
                            posCur = Pair(curIn.first, curIn.second - 1)
                        }
                    }
                }
            }
        }

        if (posCur.first == 0 && posCur.second == 0) {
            posCur = posCurseur
            tabEnergie[0][0] = '#'
        }
        return posCur
    }


    var maxTiles = 0

    // 1
    for (i in DebL .. FinL) {
        fluxActif = 0
        lstFlux.add(mutableListOf())
        posCurseur = Pair(i, DebC)
        lstFlux[fluxActif].add(posCurseur)
        tabDirFlux.add('E')
        tabEnergie[i][DebC] = '#'
        var isEnd = false

        // parcours du tableau initial
        while (!isEnd) {
            var oldCurseur = posCurseur
            when (tabDirFlux[fluxActif]) {
                'E' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'E', posCurseur)
                }
                'W' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'W', posCurseur)
                }
                'N' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'N', posCurseur)
                }
                'S' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'S', posCurseur)
                }
            }
            // halte du flux
            if (posCurseur == oldCurseur) {
                if (fluxActif == lstFlux.size - 1)
                    isEnd = true
                else {
                    fluxActif++
                    posCurseur = lstFlux[fluxActif][0]
                }
            }
        }

        maxTiles = maxOf(maxTiles, calculEnergie())
        cleanVar()
    }

    // 2
    for (i in DebL .. FinL) {
        fluxActif = 0
        lstFlux.add(mutableListOf())
        posCurseur = Pair(i, FinC)
        lstFlux[fluxActif].add(posCurseur)
        tabDirFlux.add('W')
        tabEnergie[i][FinC] = '#'
        var isEnd = false

        // parcours du tableau initial
        while (!isEnd) {
            var oldCurseur = posCurseur
            when (tabDirFlux[fluxActif]) {
                'E' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'E', posCurseur)
                }
                'W' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'W', posCurseur)
                }
                'N' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'N', posCurseur)
                }
                'S' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'S', posCurseur)
                }
            }
            // halte du flux
            if (posCurseur == oldCurseur) {
                if (fluxActif == lstFlux.size - 1)
                    isEnd = true
                else {
                    fluxActif++
                    posCurseur = lstFlux[fluxActif][0]
                }
            }
        }

        maxTiles = maxOf(maxTiles, calculEnergie())
        cleanVar()
    }

    // 3
    for (i in DebC .. FinC) {
        fluxActif = 0
        lstFlux.add(mutableListOf())
        posCurseur = Pair(DebL, i)
        lstFlux[fluxActif].add(posCurseur)
        tabDirFlux.add('S')
        tabEnergie[DebL][i] = '#'
        var isEnd = false

        // parcours du tableau initial
        while (!isEnd) {
            var oldCurseur = posCurseur
            when (tabDirFlux[fluxActif]) {
                'E' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'E', posCurseur)
                }
                'W' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'W', posCurseur)
                }
                'N' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'N', posCurseur)
                }
                'S' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'S', posCurseur)
                }
            }
            // halte du flux
            if (posCurseur == oldCurseur) {
                if (fluxActif == lstFlux.size - 1)
                    isEnd = true
                else {
                    fluxActif++
                    posCurseur = lstFlux[fluxActif][0]
                }
            }
        }

        maxTiles = maxOf(maxTiles, calculEnergie())
        cleanVar()
    }

    // 4
    for (i in DebC .. FinC) {
        fluxActif = 0
        lstFlux.add(mutableListOf())
        posCurseur = Pair(FinL, i)
        lstFlux[fluxActif].add(posCurseur)
        tabDirFlux.add('N')
        tabEnergie[FinL][i] = '#'
        var isEnd = false

        // parcours du tableau initial
        while (!isEnd) {
            val oldCurseur = posCurseur
            when (tabDirFlux[fluxActif]) {
                'E' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'E', posCurseur)
                }
                'W' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'W', posCurseur)
                }
                'N' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'N', posCurseur)
                }
                'S' -> {
                    carLu = tabCircuit[posCurseur.first][posCurseur.second]
                    posCurseur = traiteCarLu(carLu, 'S', posCurseur)
                }
            }
            // halte du flux
            if (posCurseur == oldCurseur) {
                if (fluxActif == lstFlux.size - 1)
                    isEnd = true
                else {
                    fluxActif++
                    posCurseur = lstFlux[fluxActif][0]
                }
            }
        }

        maxTiles = maxOf(maxTiles, calculEnergie())
        cleanVar()
    }

    println(maxTiles)
}
