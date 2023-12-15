
fun main() {
    val inLines: List<String>

    val bTest = false
    if (bTest) {
        inLines = readInput("test15_1")
    } else {
        inLines = readInput("input15_1")
    }

    var rep = 0L
    val lstHash = mutableListOf<Long>()

    fun calcHash(vIn:Int) : Int {
        var v = vIn * 17
        v %= 256
        return v
    }

    val lstPat = inLines[0].split(",")
    lstPat.forEach {
        var vt = 0
        it.forEach { car ->
            var v = car.code
            v += vt
            vt = calcHash(v)
        }
        lstHash.add(vt.toLong())
    }

    rep = lstHash.sum()
    println(rep)
}
