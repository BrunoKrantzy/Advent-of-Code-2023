
fun main() {
    //val inLines = readInput("test08_1")
    val inLines = readInput("input08_1")

    val mapSpots = mutableMapOf<String, Pair<String, String>>()
    val patLine = "([A-Z]+)\\s=\\s\\(([A-Z]+),\\s([A-Z]+)\\)".toRegex()

    val seqList = inLines[0]
    val lenSeq = seqList.length

    for (i in 2 until inLines.size) {
        var match = patLine.find(inLines[i])
        var spot = match!!.groups[1]!!.value
        var spotL = match.groups[2]!!.value
        var spotR = match.groups[3]!!.value

        mapSpots[spot] = Pair(spotL, spotR)
    }

    var posSeq = -1
    var spotSource = "AAA"
    var spotCible = ""
    var numSteps = 0

    while (true) {
        posSeq++
        if (posSeq == lenSeq) posSeq = 0
        val dir = seqList[posSeq]
        when (dir) {
            'L' -> spotCible = mapSpots[spotSource]!!.first
            'R' -> spotCible = mapSpots[spotSource]!!.second
        }
        spotSource = spotCible
        numSteps++
        if (spotSource == "ZZZ")
            break
    }

    println(numSteps)
}

