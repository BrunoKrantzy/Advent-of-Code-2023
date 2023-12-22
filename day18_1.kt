
import kotlin.math.abs

fun main() {
    val inLines: List<String>

    val bTest = false
    if (bTest) {
        inLines = readInput("test18_1")
    }
    else {
        inLines = readInput("input18_1")
    }

    var posR = 0
    var posL = 0
    var posD = 0
    var posU = 0
    var maxR = 0
    var maxL = 0
    var maxU = 0
    var maxD = 0
    var posCol = 0
    var posLig = 0

    var patLine = "([A-Z]) (\\d+) \\(#([[a-z]|\\d*]*)\\)".toRegex()
    var lstCom = mutableListOf<Pair<String, Int>>()


    inLines.forEach {
        var match = patLine.find(it)
        if (match != null) {
            var dir = match.groups[1]!!.value
            var n = match.groups[2]!!.value.toInt()
            lstCom.add(Pair(dir, n))
            when (dir) {
                "R" -> {
                    posR = posCol + n
                    maxR = maxOf(maxR, posR)
                    posCol += n
                }
                "L" -> {
                    posL = posCol - n
                    maxL = minOf(maxL, posL)
                    posCol -= n
                }
                "U" -> {
                    posU = posLig - n
                    maxU = minOf(maxU, posU)
                    posLig -= n
                }
                "D" -> {
                    posD = posLig + n
                    maxD = maxOf(maxD, posD)
                    posLig += n
                }
            }
        }
    }

    var hauteur = 1 + abs(maxU) + maxD
    var largeur = 1 + abs(maxL) + maxR

    posCol = (largeur - maxR) - 1
    posLig = (hauteur - maxD) - 1

    var tabPos = Array(hauteur) { Array(largeur) { '.' } }

    var d = lstCom.first()
    var f = lstCom.last()

    var posInt = ""
    if (d.first == "R") {
        if (f.first == "U") {
            posInt = "S"
        }
    }
    if (posInt != "S") {
        println("Position de départ à préciser")
        return
    }
    // par défaut je considère que le départ se fait par la droite et l'arrivée du sud
    // pour positionner la ligne intérieure du lagoon en @

    lstCom.forEach {
        val com = it.first
        val n = it.second
        when (com) {
            "R" -> {
                for (i in 0..n) {
                    tabPos[posLig][posCol+i] = '#'
                    if (tabPos[posLig+1][posCol+i] == '.')
                        tabPos[posLig+1][posCol+i] = '@'
                }
                posCol += n
            }
            "L" -> {
                for (i in 0..n) {
                    tabPos[posLig][posCol-i] = '#'
                    if (tabPos[posLig-1][posCol-i] == '.')
                        tabPos[posLig-1][posCol-i] = '@'
                }
                posCol -= n
            }
            "D" -> {
                for (i in 0..n) {
                    tabPos[posLig+i][posCol] = '#'
                    if (tabPos[posLig+i][posCol-1] == '.')
                        tabPos[posLig+i][posCol-1] = '@'
                }
                posLig += n
            }
            "U" -> {
                for (i in 0..n) {
                    tabPos[posLig-i][posCol] = '#'
                    if (tabPos[posLig-i][posCol+1] == '.')
                        tabPos[posLig-i][posCol+1] = '@'
                }
                posLig -= n
            }
        }
    }

    // remplissage du lagoon
    val lstLigTablo = mutableListOf<String>()
    tabPos.forEachIndexed { idxL, lig ->
        var str = ""
        lig.forEachIndexed { idxC, car ->
            str += car
        }
        while (str.contains("@.")) {
            str = str.replace("@.", "@@")
        }
        while (str.contains(".@")) {
            str = str.replace(".@", "@@")
        }
        lstLigTablo.add(str)
    }

    // comptage du cubage du lagoon
    var rep = hauteur * largeur
    var points = 0
    lstLigTablo.forEach {
        it.forEach { car ->
            if (car == '.') points++
        }
    }

    println(rep - points)
}


