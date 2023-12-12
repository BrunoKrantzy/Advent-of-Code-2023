
fun main() {

    var inLines: List<String>
    var nLine = 142
    var nCol = 142

    val bTest = false
    if (bTest) {
        inLines = readInput("test10_1")
        nLine = 7
        nCol = 7
    }
    else {
        inLines = readInput("input10_1")
    }

    data class Pipe(var idC1:Int, var idC2:Int)

    var tabPos = Array(nLine) { Array(nCol) { -1 } }
    var tabCar = Array(nLine) { Array(nCol) { '@' } }
    var mapPipes = mutableMapOf<Int, Pipe>()
    var pStart = 0
    var nCar = nCol-1
    var noL = 0
    var noC = 0

    inLines.forEach {
        nCar += 2
        noL++
        noC = 0
        it.forEach { car ->
            nCar++
            noC++
            if (car == 'S') pStart = nCar
            tabPos[noL][noC] = nCar
            tabCar[noL][noC] = car
        }
    }

    fun buildPipes(nCar:Char, cCar:Int, l:Int, c:Int) : Pipe {
        val pPipe = Pipe(-1, -1)

        when (nCar) {
            '|' -> {
                if (l == 1) {
                    pPipe.idC1 = -1
                    pPipe.idC2 = cCar+nLine
                }
                else if (l == nLine-2) {
                    pPipe.idC1 = cCar-nLine
                    pPipe.idC2 = -1
                }
                else {
                    pPipe.idC1 = cCar-nLine
                    pPipe.idC2 = cCar+nLine
                }
            }
            '-' -> {
                if (c == 1) {
                    pPipe.idC1 = -1
                    pPipe.idC2 = cCar+1
                }
                else if (c == nCol-2) {
                    pPipe.idC1 = cCar-1
                    pPipe.idC2 = -1
                }
                else {
                    pPipe.idC1 = cCar-1
                    pPipe.idC2 = cCar+1
                }
            }
            'L' -> {
                if (l == 1)
                    pPipe.idC1 = -1
                else
                    pPipe.idC1 = cCar-nLine

                if (c == nCol-2)
                    pPipe.idC2 = -1
                else
                    pPipe.idC2 = cCar+1
            }
            'J' -> {
                if (l == 1)
                    pPipe.idC1 = -1
                else
                    pPipe.idC1 = cCar-nLine

                if (c == 1)
                    pPipe.idC2 = -1
                else
                    pPipe.idC2 = cCar-1
            }
            '7' -> {
                if (c == 1)
                    pPipe.idC1 = -1
                else
                    pPipe.idC1 = cCar-1

                if (l == nLine-2)
                    pPipe.idC2 = -1
                else
                    pPipe.idC2 = cCar+nLine
            }
            'F' -> {
                if (c == nCol-2)
                    pPipe.idC1 = -1
                else
                    pPipe.idC1 = cCar+1

                if (l == nLine-2)
                    pPipe.idC2 = -1
                else
                    pPipe.idC2 = cCar+nLine
            }
        }

        return pPipe
    }

    for (l in 1 until nLine -1) {
        for (c in 1 until nCol - 1) {
            val car = tabCar[l][c]
            val cCar = tabPos[l][c]
            if (car != '.' && car != '@') {
                val pPipe = buildPipes(car, cCar, l, c)
                mapPipes[cCar] = pPipe
            }
        }
    }

    // nett. mapPipes
    mapPipes.forEach {
        if (!mapPipes.containsKey(it.value.idC1))
            it.value.idC1 = -1
        if (!mapPipes.containsKey(it.value.idC2))
            it.value.idC2 = -1
    }

    // mapPipe de démarrage
    mapPipes.forEach {
        if (it.value.idC1 == pStart) {
            if (mapPipes[pStart]!!.idC1 == -1)
                mapPipes[pStart]!!.idC1 = it.key
            else if (mapPipes[pStart]!!.idC2 == -1)
                mapPipes[pStart]!!.idC2 = it.key
        }
        else if (it.value.idC2 == pStart) {
            if (mapPipes[pStart]!!.idC1 == -1)
                mapPipes[pStart]!!.idC1 = it.key
            else if (mapPipes[pStart]!!.idC2 == -1)
                mapPipes[pStart]!!.idC2 = it.key
        }
    }

    // deplacement
    var farthest = 0
    var setStreets = mutableListOf<Int>()
    if (mapPipes[pStart]!!.idC1 != -1)
        setStreets.add(mapPipes[pStart]!!.idC1)
    if (mapPipes[pStart]!!.idC2 != -1)
        setStreets.add(mapPipes[pStart]!!.idC2)

    var lstVisited = mutableListOf<Int>()
    var next = 0

    setStreets.forEach {
        lstVisited.add(it)

        var v1 = mapPipes[it]!!.idC1
        var v2 = mapPipes[it]!!.idC2

        while (true) {
            if (v1 != -1 && !lstVisited.contains(v1)) {
                next = v1
            }
            else if (v2 != -1 && !lstVisited.contains(v2)) {
                next = v2
            }
            lstVisited.add(next)

            if (mapPipes.containsKey(next)) {
                val p = mapPipes[next]
                v1 = p!!.idC1
                v2 = p.idC2
            }
            else {
                break
            }

            if (lstVisited.contains(v1) && lstVisited.contains(v2))
                break
        }

        farthest = maxOf(farthest, lstVisited.size)
        lstVisited.clear()
    }

    println(farthest / 2)
}

