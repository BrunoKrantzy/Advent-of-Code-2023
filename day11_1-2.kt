import kotlin.math.abs

fun main() {
    var inLines: List<String>
    var nLine = 140
    var nCol = 140

    val bTest = false
    if (bTest) {
        inLines = readInput("test11_1")
        nLine = 10
        nCol = 10
    }
    else {
        inLines = readInput("input11_1")
    }

    var rep = 0L
    var numSharp = 0
    var vAj = 999999 // 1 pour partie 1

    var tab1 = Array(nLine) { Array(nCol) { '@' } }

    // lignes des #
    var tabLigneSharp = mutableListOf<Int>()
    for (l in 0 until nLine) {
        var noSharp = true
        for (c in 0 until nCol) {
            val car = inLines[l][c]
            tab1[l][c] = car
            if (car != '.') {
                numSharp++
                noSharp = false
            }
        }
        if (noSharp)
            tabLigneSharp.add(l)
    }

    // correction selon ajout
    var tabCorL = mutableListOf<Pair<Int, Int>>()
    var ajout = 0
    for (i in 0 until nLine) {
        if (tabLigneSharp.contains(i))
            ajout += vAj
        tabCorL.add(Pair(i, i+ajout))
    }

    // colonnes des #
    var tabColSharp = mutableListOf<Int>()
    for (c in 0 until nCol) {
        var noSharp = true
        for (l in 0 until nLine) {
            val car = inLines[l][c]
            tab1[l][c] = car
            if (car != '.') {
                numSharp++
                noSharp = false
            }
        }
        if (noSharp)
            tabColSharp.add(c)
    }

    // correction selon ajout
    var tabCorC = mutableListOf<Pair<Int, Int>>()
    ajout = 0
    for (i in 0 until nCol) {
        if (tabColSharp.contains(i))
            ajout += vAj
        tabCorC.add(Pair(i, i+ajout))
    }

    // liste de toutes les paires de #
    var lstPosSharp = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until nLine) {
        for (j in 0 until nCol) {
            if (tab1[i][j] == '#') {
                val v1 = tabCorL[i].second // valeur corrigée
                val v2 = tabCorC[j].second // valeur corrigée
                val p = Pair(v1, v2)
                lstPosSharp.add(p)
            }
        }
    }

    // calcul final
    for (i in 0 until lstPosSharp.size) {
        val p1 = lstPosSharp[i]
        for (j in i+1 until lstPosSharp.size) {
            val p2 = lstPosSharp[j]

            var manhatDist = abs(p1.first - p2.first) + abs(p1.second - p2.second)
            rep += manhatDist
        }
    }

    println(rep)
}

