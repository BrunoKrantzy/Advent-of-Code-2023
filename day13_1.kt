import kotlin.math.abs

fun main() {

    val inLines: List<String>

    val bTest = false
    if (bTest) {
        inLines = readInput("test13_1")
    } else {
        inLines = readInput("input13_1")
    }

    val lstPatterns = mutableListOf<MutableList<String>>()
    val lstTemp = mutableListOf<String>()
    inLines.forEach {
        if (it.isEmpty()) {
            lstPatterns.add(lstTemp.toMutableList())
            lstTemp.clear()
        }
        else
            lstTemp.add(it)
    }
    lstPatterns.add(lstTemp)

    var nbRows = 0
    var nbCols = 0
    var nbL = 0
    var nbC = 0

    // rotation du plateau pour traiter le vertical
    fun turnPlato(platoIn: MutableList<String>) : MutableList<String> {
        var platoOut = mutableListOf<String>()
        val nl = platoIn.size
        val nc = platoIn.first().length
        for (col in 0 until  nc) {
            val str = StringBuilder()
            for (li in nl-1 downTo  0) {
                str.append(platoIn[li][col])
            }
            platoOut.add(str.toString())
            str.clear()
        }
        return platoOut
    }

    data class Result(val res:Boolean, val goodLines:Int)

    fun testSuitePrem(pAt:MutableList<String>, ligBase:Int, ligLast:Int, ligneDep: Int) : Result {
        var ligDep = ligneDep
        var goodLi = 2 + abs(ligLast - ligDep)
        var res = Result(false, 0)
        var nL = 1 + abs((ligLast - ligBase))
        var ligMatch = ligLast
        var ligFirst = ligBase
        var test = true
        for (i in 1 .. nL/2) {
            val l1 = pAt[ligFirst]
            val l2 = pAt[ligMatch]
            if (l1 != l2) {
                test = false
                goodLi = 0
                break
            }
            else {
                ligFirst++
                ligMatch--
            }
        }
        res = Result(test, goodLi)
        return res
    }

    fun testSuiteDer(pAt:MutableList<String>, ligBase:Int, ligLast:Int, ligneDep: Int) : Result {
        var ligDep = ligneDep
        var goodLi = abs(ligLast - ligDep)
        var res = Result(false, 0)
        var nL = 1 + abs((ligLast - ligBase))
        var ligMatch = ligLast
        var ligFirst = ligBase
        var test = true
        for (i in 1 .. nL/2) {
            val l1 = pAt[ligFirst]
            val l2 = pAt[ligMatch]
            if (l1 != l2) {
                test = false
                goodLi = 0
                break
            }
            else {
                ligFirst++
                ligMatch--
            }
        }
        res = Result(test, goodLi)
        return res
    }


    var lstValPosH = mutableListOf<Int>()
    var lstValPosV = mutableListOf<Int>()

    lstPatterns.forEach {
        var pH = it
        var pV = turnPlato(pH)

        // traitement pH
        var patHgood = false

        // première ligne valide ?
        // on cherche en partant de la dernière ligne celle qui match avec la 1ere
        nbL = pH.size
        var nbRowsTemp1 = 0
        var ligBase = 0
        var firstLig = pH[ligBase]
        var ligneDep = 0

        for (ll in 0 .. 0) {
            for (ligSuiv in nbL-1 downTo ligBase + 1) {
                val str = pH[ligSuiv]
                if (firstLig == str && (1 + (ligSuiv - ligBase)) % 2 == 0) {
                    ligneDep = 0
                    // vérifier le matching des lignes suivantes
                    val result = testSuitePrem(pH, ligBase + 1, ligSuiv - 1, ligneDep)
                    if (result.res == true) {
                        nbRowsTemp1 = maxOf(nbRowsTemp1, ligneDep + result.goodLines / 2)
                        patHgood = true
                        break
                    }
                }
            }
        }


        // dernière ligne valide ?
        var nbRowsTemp2 = 0
        ligBase = pH.size - 1
        firstLig = pH[ligBase]

        if (!patHgood) {
            for (ll in 0 .. 0) {
                for (ligSuiv in pH.size-1 downTo 1) {
                    val str = pH[ligSuiv]
                    if (firstLig == str && (1 + (ligBase - ligSuiv)) % 2 == 0) {
                        ligneDep = ligSuiv
                        // vérifier le matching des lignes suivantes
                        val result = testSuiteDer(pH, ligSuiv + 1, ligBase - 1, ligneDep)
                        if (result.res == true) {
                            nbRowsTemp2 = maxOf(nbRowsTemp1, ligneDep + ((2 + result.goodLines) / 2))
                            patHgood = true
                            break
                        }
                    }
                }
            }
        }

        nbRows += maxOf(nbRowsTemp1, nbRowsTemp2)


        // traitement pV

        // première ligne valide ?
        // on cherche en partant de la dernière ligne celle qui match avec la 1ere
        nbL = pV.size
        var patVgood = false
        var nbColsTemp1 = 0
        ligBase = 0
        firstLig = pV[ligBase]

        for (ll in 0 .. 0) {
            for (ligSuiv in nbL-1 downTo ligBase + 1) {
                val str = pV[ligSuiv]
                if (firstLig == str && (1 + (ligSuiv - ligBase)) % 2 == 0) {
                    ligneDep = 0
                    // vérifier le matching des lignes suivantes
                    val result = testSuitePrem(pV, ligBase + 1, ligSuiv - 1, ligneDep)
                    if (result.res == true) {
                        nbColsTemp1 = maxOf(nbColsTemp1, ligneDep + result.goodLines / 2)
                        patVgood = true
                        break
                    }
                }
            }
        }

        // dernière ligne valide ?
        var nbColsTemp2 = 0
        ligBase = pV.size - 1
        firstLig = pV[ligBase]

        if (!patVgood) {
            for (ll in 0 .. 0) {
                for (ligSuiv in pV.size-1 downTo 1) {
                    val str = pV[ligSuiv]
                    if (firstLig == str && (1 + (ligBase - ligSuiv)) % 2 == 0) {
                        ligneDep = ligSuiv
                        // vérifier le matching des lignes suivantes
                        val result = testSuiteDer(pV, ligSuiv + 1, ligBase - 1, ligneDep)
                        if (result.res == true) {
                            nbColsTemp2 = maxOf(nbColsTemp1, ligneDep + ((2 + result.goodLines) / 2))
                            patVgood = true
                            break
                        }
                    }
                }
            }
        }

        nbCols += maxOf(nbColsTemp1, nbColsTemp2)

        lstValPosH.clear()
        lstValPosV.clear()
    }

    println((nbRows * 100) + nbCols)
}
