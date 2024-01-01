
fun main() {
    val inLines: List<String>

    var nbL:Int
    var nbC:Int
    var entree:Pair<Int,Int>
    var sortie:Pair<Int,Int>

    val bTest = false
    if (bTest) {
        inLines = readInput("test23_1")
        nbL = 23
        nbC = 23
        entree = Pair(0,1)
        sortie = Pair(22,21)
    } else {
        inLines = readInput("input23_1")
        nbL = 141
        nbC = 141
        entree = Pair(0,1)
        sortie = Pair(140,139)
    }

    val tabInput = Array(nbL) { Array(nbC) { '.' } }

    data class Bifurc(var voie: Pair<Int, Int>, var valDep:Int, var setVisited:MutableSet<Pair<Int, Int>>)
    var queueBifurcs = ArrayDeque<Bifurc>()

    inLines.forEachIndexed { idx, it ->
        for (i in 0 until nbC) {
            tabInput[idx][i] = it[i]
        }
    }

    var nbPoints = 0
    var maxPoints = 0
    val setPosVisited = mutableSetOf<Pair<Int, Int>>()

    fun voiesPossiblesDepuis(dep:Pair<Int, Int>) : MutableList<Pair<Int, Int>> {
        val liste = mutableListOf<Pair<Int, Int>>()
        when (tabInput[dep.first][dep.second]) {
            // /*
            'v' -> {
                if (!setPosVisited.contains(Pair(dep.first+1, dep.second)))
                    liste.add(Pair(dep.first+1, dep.second))
            }
            '>' -> {
                if (!setPosVisited.contains(Pair(dep.first, dep.second+1)))
                    liste.add(Pair(dep.first, dep.second+1))
            }
            // */
            else -> {
                if (dep.first > 0)
                    if (tabInput[dep.first-1][dep.second] != '#' && !setPosVisited.contains(Pair(dep.first-1, dep.second)))
                        liste.add(Pair(dep.first-1, dep.second))

                if (dep.first < nbL-1)
                    if (tabInput[dep.first+1][dep.second] != '#' && !setPosVisited.contains(Pair(dep.first+1, dep.second)))
                        liste.add(Pair(dep.first+1, dep.second))

                if (dep.second < nbC-1)
                    if (tabInput[dep.first][dep.second+1] != '#' && !setPosVisited.contains(Pair(dep.first, dep.second+1)))
                        liste.add(Pair(dep.first, dep.second+1))

                if (dep.second > 0)
                    if (tabInput[dep.first][dep.second-1] != '#' && !setPosVisited.contains(Pair(dep.first, dep.second-1)))
                        liste.add(Pair(dep.first, dep.second-1))
            }
        }

        return liste
    }

    var pos = entree
    setPosVisited.add(pos)

    var finParcours = false
    var finBifurcs = false

    while (!finBifurcs) {
        while (!finParcours) {
            val vPoss = voiesPossiblesDepuis(pos)
            when (vPoss.size) {
                0 -> finParcours = true // fin de parcours
                1 -> {
                    pos = vPoss[0]
                    nbPoints++
                }
                else -> {
                    pos = vPoss[0]
                    nbPoints++
                    for (p in 1 until vPoss.size) {
                        var visited = mutableSetOf<Pair<Int, Int>>()
                        visited.addAll(setPosVisited)
                        val bif = Bifurc(vPoss[p], nbPoints, visited)
                        queueBifurcs.add(bif)
                    }
                }
            }
            setPosVisited.add(pos)
        }

        if (pos == sortie) {
            maxPoints = maxOf(maxPoints, nbPoints)
            println("sortie : $nbPoints")
        }

        if (queueBifurcs.size > 0) {
            finParcours = false
            pos = queueBifurcs.first().voie
            nbPoints = queueBifurcs.first().valDep

            setPosVisited.clear()
            setPosVisited.addAll(queueBifurcs.first().setVisited)
            queueBifurcs.removeFirst()
        }
        else {
            finBifurcs = true
        }
    }

    println("maxPoints : $maxPoints")
}

