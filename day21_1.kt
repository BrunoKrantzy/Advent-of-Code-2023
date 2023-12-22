
fun main() {
    val inLines: List<String>

    var maxSteps: Int
    val bTest = false
    inLines = if (bTest) {
        maxSteps = 6
        readInput("test21_1")
    } else {
        maxSteps = 64
        readInput("input21_1")
    }

    val nbCol = inLines[0].length
    val nbLig = inLines.size

    data class PosStep(var step:Int, var pos:Pair<Int, Int>)

    var tabIn = Array(nbLig) { Array(nbCol) { '@' } }
    var lstTilesAtraiter = ArrayDeque<Pair<Int, Int>>()
    var setTilesPossibles = mutableSetOf<PosStep>()

    var step = 0
    var nbTiles = 0

    var posS = Pair(0, 0)

    inLines.forEachIndexed { idxL, lig ->
        lig.forEachIndexed { idxC, car ->
            tabIn[idxL][idxC] = car
            if (car == 'S') {
                tabIn[idxL][idxC] = '.'
                posS = Pair(idxL, idxC)
            }
        }
    }
    lstTilesAtraiter.add(posS)

    fun searchTiles(pos:Pair<Int, Int>, step:Int) : MutableList<Pair<Int, Int>> {
        var lstFreeTiles = mutableListOf<Pair<Int,Int>>()

        var tileN = Pair(pos.first, pos.second)
        var endNorth = false
        var tileE = Pair(pos.first, pos.second)
        var endEast = false
        var tileS = Pair(pos.first, pos.second)
        var endSouth = false
        var tileW = Pair(pos.first, pos.second)
        var endWest = false

        for (i in 1..1) {
            if (!endNorth && pos.first - i >= 0 && tabIn[tileN.first - 1][tileN.second] == '.')
                tileN = Pair(pos.first - 1, pos.second)
            else
                endNorth = true

            if (!endSouth && pos.first + i < nbLig && tabIn[tileS.first + 1][tileS.second] == '.')
                    tileS = Pair(pos.first + 1, pos.second)
            else
                endSouth = true

            if (!endWest && pos.second - i >= 0 && tabIn[tileW.first][tileW.second - 1] == '.')
                tileW = Pair(pos.first, pos.second - 1)
            else
                endWest = true

            if (!endEast && pos.second + i < nbCol && tabIn[tileE.first][tileE.second + 1] == '.')
                tileE = Pair(pos.first, pos.second + 1)
            else
                endEast = true
        }

        if (!endNorth && !setTilesPossibles.contains(PosStep(step, tileN))) {
            lstFreeTiles.add(tileN)
            nbTiles++
            setTilesPossibles.add(PosStep(step, tileN))
        }

        if (!endSouth && !setTilesPossibles.contains(PosStep(step, tileS))) {
            lstFreeTiles.add(tileS)
            nbTiles++
            setTilesPossibles.add(PosStep(step, tileS))
        }

        if (!endEast && !setTilesPossibles.contains(PosStep(step, tileE))) {
            lstFreeTiles.add(tileE)
            nbTiles++
            setTilesPossibles.add(PosStep(step, tileE))
        }

        if (!endWest && !setTilesPossibles.contains(PosStep(step, tileW))) {
            lstFreeTiles.add(tileW)
            nbTiles++
            setTilesPossibles.add(PosStep(step, tileW))
        }

        return lstFreeTiles
    }

    var beforeLastStep = 0
    while (step < maxSteps) {
        step++
        val freeTiles = mutableListOf<Pair<Int, Int>>()

        while (lstTilesAtraiter.size > 0) {
            var posAtraiter = lstTilesAtraiter.removeFirst()
            freeTiles.addAll(searchTiles(posAtraiter, step))
        }
        lstTilesAtraiter.addAll(freeTiles)
        freeTiles.clear()

        println("$step : $nbTiles")
        if (step == maxSteps-1)
            beforeLastStep = nbTiles
    }

    println(nbTiles - beforeLastStep)
}

