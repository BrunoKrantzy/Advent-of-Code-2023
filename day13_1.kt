import kotlin.math.abs

fun main() {

    var inLines: List<String>

    val bTest = true
    if (bTest) {
        inLines = readInput("test13_1")
    } else {
        inLines = readInput("input13_1")
    }

    var lstPatterns = mutableListOf<MutableList<String>>()
    var lstTemp = mutableListOf<String>()
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

    // rotation du plateau permettant la future rech/rep.
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

    fun testSuite(pAt:MutableList<String>, ligBase:Int, ligLast:Int) : Result {
        var goodLi = abs(ligLast - ligBase) + 1
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
        nbL = pH.size
        nbC = pH[0].length
        var patHgood = false

        // première ligne valide ?
        // on cherche en partant de la dernière ligne celle qui match avec la 1ere
        var nbRowsTemp1 = 0
        var ligBase = 0
        var firstLig = pH[ligBase]

        for (ll in 1 .. nbL-2) {
            for (ligSuiv in nbL-ll downTo ligBase + 1) {
                val str = pH[ligSuiv]
                if (firstLig == str && (1 + (ligSuiv - ligBase)) % 2 == 0) {
                    // vérifier le matching des lignes suivantes
                    val result = testSuite(pH, ligBase + 1, ligSuiv - 1)
                    if (result.res == true) {
                        nbRowsTemp1 = maxOf(nbRowsTemp1, result.goodLines)
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

        for (ll in 0 .. nbL-2) {
            for (ligSuiv in ll until pH.size-1) {
                val str = pH[ligSuiv]
                if (firstLig == str && (1 + (ligBase - ligSuiv)) % 2 == 0) {
                    // vérifier le matching des lignes suivantes
                    val result = testSuite(pH, ligSuiv + 1, ligBase - 1)
                    if (result.res == true) {
                        nbRowsTemp2 = maxOf(nbRowsTemp2, result.goodLines)
                        patHgood = true
                        break
                    }
                }
            }
        }

        nbRows += maxOf(nbRowsTemp1, nbRowsTemp2)



        // traitement pV
        nbL = pV.size
        var patVgood = false
        var nbColsTemp1 = 0
        ligBase = 0
        firstLig = pV[ligBase]

        for (ll in 1 .. nbL-2) {
            for (ligSuiv in nbL-ll downTo ligBase + 1) {
                val str = pV[ligSuiv]
                if (firstLig == str && (1 + (ligSuiv - ligBase)) % 2 == 0) {
                    // vérifier le matching des lignes suivantes
                    val result = testSuite(pV, ligBase + 1, ligSuiv - 1)
                    if (result.res == true) {
                        nbColsTemp1 = 1 + ((2 + result.goodLines) / 2)
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

        for (ll in 0 .. nbL-2) {
            for (ligSuiv in ll until pV.size-1) {
                val str = pV[ligSuiv]
                if (firstLig == str && (1 + (ligBase - ligSuiv)) % 2 == 0) {
                    // vérifier le matching des lignes suivantes
                    val result = testSuite(pV, ligSuiv + 1, ligBase - 1)
                    if (result.res == true) {
                        nbColsTemp2 = maxOf(nbColsTemp1, 1 + (2 + result.goodLines) / 2)
                        patVgood = true
                        break
                    }
                }
            }
        }

        if (patVgood && patHgood)
            println("ERR 1")
        if (!patVgood && !patHgood)
            println("ERR 2")

        nbCols += maxOf(nbColsTemp1, nbColsTemp2)

        lstValPosH.clear()
        lstValPosV.clear()
    }

    println((nbRows * 100) + nbCols)
}
