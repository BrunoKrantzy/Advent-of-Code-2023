
fun verifZone(pairCoor: Pair<Int, Int>, lstSymb: MutableList<Pair<Int, Int>>) : Boolean {
    var inZ = false
    var lig = pairCoor.first
    var col = pairCoor.second
    for (l in lig-1..lig+1) {
        for (c in col-1..col+1) {
            val p = Pair(l, c)
            if (lstSymb.contains(p)) {
                inZ = true
                break
            }
        }
    }
    return inZ
}


fun main() {

    val inLines = readInput("input03_1")
    //val inLines = readInput("test03_1")

    var rep = 0

    var lTab = 142 // 12
    var cTab = 142 // 12

    var lstSymboles = mutableListOf<Pair<Int, Int>>()
    var tabSymboles = Array(lTab) { Array(cTab) { '.' } }

    for (i in 0..lTab-3) {
        val line = inLines[i]
        for (j in 0..cTab-3) {
            val car = line[j]
            if (car != '.' && !car.isDigit())
                tabSymboles[i+1][j+1] = '@'
            else if (car.isDigit())
                tabSymboles[i+1][j+1] = car
        }
    }

    for (i in 1..lTab-1) {
        for (j in 1..cTab-1) {
            val car = tabSymboles[i][j]
            if (car == '@')
            lstSymboles.add(Pair(i, j))
        }
    }

    val strNombre = StringBuilder()
    var inNombre = false
    var inZone = false

    for (i in 1..lTab-1) {
        for (j in 1..cTab-1) {
            val car = tabSymboles[i][j]
            if (car.isDigit()) {
                strNombre.append(car)
                inNombre = true
                if (!inZone)
                    inZone = verifZone(Pair(i, j), lstSymboles)
            }
            else {
                if (inNombre) {
                    val vNombre = strNombre.toString().toInt()
                    if (inZone)
                        rep += vNombre
                    inNombre = false
                    inZone = false
                    strNombre.clear()
                }
            }
        }
    }

    println(rep)
}


