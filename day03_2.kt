
data class Datas (var pos: Pair<Int, Int>, var n1: Int, var n2: Int, var compter: Boolean)
data class zoneAster (var pos: Pair<Int, Int>, var inZ: Boolean)

fun verifZoneAster(pairCoor: Pair<Int, Int>, lstSymb: MutableList<Pair<Int, Int>>) : zoneAster {
    val lig = pairCoor.first
    val col = pairCoor.second
    var zA = zoneAster(Pair(-1, -1), false)
    for (l in lig-1..lig+1) {
        for (c in col-1..col+1) {
            val p = Pair(l, c)
            if (lstSymb.contains(p)) {
                zA = zoneAster(Pair(l, c), true)
                break
            }
        }
    }
    return zA
}


fun main() {

    val inLines = readInput("input03_1")
    //val inLines = readInput("test03_1")

    var rep = 0L
    val lTab = 142 // 12
    val cTab = 142 // 12

    val tabSymboles = Array(lTab) { Array(cTab) { '.'} }
    val tabDatas = Array(500) { Datas(Pair(-1, -1), -1, -1, false) }
    val lstAster = mutableListOf<Pair<Int, Int>>()

    for (i in 0..lTab-3) {
        val line = inLines[i]
        for (j in 0..cTab-3) {
            val car = line[j]
            if (car == '*')
                tabSymboles[i+1][j+1] = '*'
            else if (car != '.' && !car.isDigit())
                tabSymboles[i+1][j+1] = '@'
            else if (car.isDigit())
                tabSymboles[i+1][j+1] = car
        }
    }

    var cpAster = -1
    for (i in 1..lTab-1) {
        for (j in 1..cTab-1) {
            val car = tabSymboles[i][j]
            if (car == '*') {
                cpAster++
                lstAster.add(Pair(i, j))
                tabDatas[cpAster] = (Datas(Pair(i, j), -1, -1, false))
            }
        }
    }

    val strNombre = StringBuilder()
    var inNombre = false
    var inZone = false
    var posAster = Pair(-1, -1)

    for (i in 1..lTab-1) {
        for (j in 1..cTab-1) {
            val car = tabSymboles[i][j]
            if (car.isDigit()) {
                strNombre.append(car)
                inNombre = true
                if (!inZone) {
                    val infosDatas = verifZoneAster(Pair(i, j), lstAster)
                    if (infosDatas.inZ) {
                        inZone = true
                        posAster = Pair(infosDatas.pos.first, infosDatas.pos.second)
                    }
                }
            }
            else {
                if (inNombre) {
                    val vNombre = strNombre.toString().toInt()
                    if (inZone) {
                        if (lstAster.contains(posAster)) {
                            val idxTabAster = lstAster.indexOf(posAster)
                            val d = tabDatas[idxTabAster]
                            if (d.n1 == -1) d.n1 = vNombre
                            else if (d.n2 == -1) d.n2 = vNombre
                            if (d.n1 != -1 && d.n2 != -1) {
                                d.compter = true
                            }
                            tabDatas[idxTabAster] = d
                        }
                    }
                    inNombre = false
                    inZone = false
                    strNombre.clear()
                }
            }
        }
    }

    tabDatas.forEach {
        if (it.compter) {
            val multi = it.n1 * it.n2
            rep += multi
        }
    }

    println(rep)
}

