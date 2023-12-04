

fun main() {

    val inLines = readInput("input04_1")
    //val inLines = readInput("test04_1")

    var rep = 0L

    var patNum = "(Card *\\d+:)([\\d+ *]+)( \\| )([\\d+ *]+)".toRegex()

    inLines.forEach {
        var noG = ""
        var noT = ""
        val match = patNum.find(it)
        if (match != null) {
            noG = match.groups[2]!!.value.trim()
            noT = match.groups[4]!!.value.trim()
        }
        val lstG = noG.split(" ")
        val lstT = noT.split(" ")

        var cp = 0
        var repLine = 0
        lstT.forEach { num ->
            if (num.isNotEmpty()) {
                if (lstG.contains(num)) {
                    cp++
                    if (cp == 1)
                        repLine = 1
                    else
                        repLine *= 2
                }
            }
        }
        rep += repLine
    }

    println(rep)
}

