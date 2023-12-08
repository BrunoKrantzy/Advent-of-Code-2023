
// + grand commun diviseur (PGCD)
tailrec fun gcd(a : Long, b : Long) : Long = if (a == 0L) b else gcd(b % a, a)

// + petit commun multiple (PPCM)
fun ppcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}


fun main() {
    //val inLines = readInput("test08_1")
    val inLines = readInput("input08_1")

    val mapSpots = mutableMapOf<String, Pair<String, String>>()
    var lstSources = mutableListOf<String>()
    val patLine = "([A-Z]+)\\s=\\s\\(([A-Z]+),\\s([A-Z]+)\\)".toRegex()

    val seqList = inLines[0]
    val lenSeq = seqList.length

    for (i in 2 until inLines.size) {
        var match = patLine.find(inLines[i])
        var spot = match!!.groups[1]!!.value
        var spotL = match.groups[2]!!.value
        var spotR = match.groups[3]!!.value

        mapSpots[spot] = Pair(spotL, spotR)
        if (spot.endsWith('A'))
            lstSources.add(spot)
    }

    var posSeq = -1
    var spotSource = ""
    var spotCible = ""
    var numSteps = 0L
    var lstNumSteps = mutableListOf<Long>()

    for (i in 0 until lstSources.size) {
        numSteps = 0
        spotSource = lstSources[i]

        while (true) {
            posSeq++
            if (posSeq == lenSeq) posSeq = 0
            val dir = seqList[posSeq]
            when (dir) {
                'L' -> spotCible = mapSpots[spotSource]!!.first
                'R' -> spotCible = mapSpots[spotSource]!!.second
            }
            numSteps++

            if (spotCible.endsWith('Z'))
                break
            else
                spotSource = spotCible
        }

        lstNumSteps.add(numSteps)
    }

    var ppcmRes = lstNumSteps[0]
    for (i in 1 until lstNumSteps.size) {
        ppcmRes = ppcm(ppcmRes, lstNumSteps[i] )
    }

    println(ppcmRes)
}

