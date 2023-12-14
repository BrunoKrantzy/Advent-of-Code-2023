
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

    val pV = mutableListOf<String>()
    val nl = inLines.size
    val nc = inLines.first().length
    for (l in 0 until nc) {
        val str = StringBuilder()
        for (c in 0 until nl) {
            str.append(inLines[c][l])
        }
        pV.add(str.toString())
        str.clear()
    }

    val pOut = mutableListOf<String>()
    for (i in 0 until pV.size) {
        var str = pV[i]
        while (str.contains(".O")) {
            str = str.replace(".O", "O.")
        }
        pOut.add(str)
    }

    pOut.forEach {
        for (j in 0 until it.length) {
            if (it[j] == 'O')
                rep += nbCols - j
        }
    }

    println(rep)
}
