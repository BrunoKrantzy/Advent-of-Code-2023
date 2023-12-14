
fun main() {

    var inLines: List<String>

    val bTest = false
    if (bTest) {
        inLines = readInput("test14_1")
    } else {
        inLines = readInput("input14_1")
    }

    val nbCols = inLines.size
    var rep = 0L
    var nbCycles = 1000

    // rotation du plateau permettant la future rech/rep.
    fun turnPlato(platoIn: MutableList<String>) : MutableList<String> {
        var platoOut = mutableListOf<String>()
        val nc = platoIn.first().length
        for (c in 0 until  nc) {
            val str = StringBuilder()
            for (l in nc-1 downTo  0) {
                str.append(platoIn[l][c])
            }
            platoOut.add(str.toString())
            str.clear()
        }
        return platoOut
    }

    var pIn = inLines as MutableList
    var pOut = mutableListOf<String>()

    for (cycle in 0 until nbCycles) {

        // North
        pOut = turnPlato(pIn)
        pIn.clear()
        for (i in 0 until pOut.size) {
            var str = pOut[i]
            while (str.contains("O.")) {
                str = str.replace("O.", ".O")
            }
            pIn.add(str)
        }

        // West
        pOut = turnPlato(pIn)
        pIn.clear()
        for (i in 0 until pOut.size) {
            var str = pOut[i]
            while (str.contains("O.")) {
                str = str.replace("O.", ".O")
            }
            pIn.add(str)
        }

        // South
        pOut = turnPlato(pIn)
        pIn.clear()
        for (i in 0 until pOut.size) {
            var str = pOut[i]
            while (str.contains("O.")) {
                str = str.replace("O.", ".O")
            }
            pIn.add(str)
        }

        // East
        pOut = turnPlato(pIn)
        pIn.clear()
        for (i in 0 until pOut.size) {
            var str = pOut[i]
            while (str.contains("O.")) {
                str = str.replace("O.", ".O")
            }
            pIn.add(str)
        }
    }

    // dernière rotation pour comtage du Nord
    pOut = turnPlato(pIn)

    for (c in nbCols-1 downTo 0) {
        for (l in 0 until nbCols) {
            if (pOut[l][c] == 'O')
                rep += c+1
        }
    }

    println(rep)
}
