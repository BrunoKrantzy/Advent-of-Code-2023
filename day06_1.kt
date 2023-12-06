
fun main() {

    val inLines = readInput("input06_1")

    var patLines = "[A-Z|a-z]+:\\s+([(\\d+)\\s*]+)".toRegex()

    var match = patLines.find(inLines[0])
    var str = match!!.groups[1]!!.value.trim()
    val lstTime = str.split("\\s+".toRegex())

    match = patLines.find(inLines[1])
    str = match!!.groups[1]!!.value.trim()
    val lstDist = str.split("\\s+".toRegex())

    val lstResults = mutableListOf<Long>()
    for (i in 0 until lstTime.size) {
        var time = lstTime[i].toLong()
        var nbCalcSup = 0L
        for (j in 1 until time) {
            time--
            if (j * time > lstDist[i].toLong())
                nbCalcSup++
        }
        lstResults.add(nbCalcSup)
    }

    var rep = 1L
    for (i in 0 until lstResults.size) {
        rep *= lstResults[i]
    }
    println(rep)
}

