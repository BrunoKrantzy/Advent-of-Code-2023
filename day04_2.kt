
fun main() {

    val inLines = readInput("input04_1")
    //val inLines = readInput("test04_1")

    val patNum = "Card *(\\d+):([\\d+ *]+)( \\| )([\\d+ *]+)".toRegex()
    val tabCards = Array(300) { 0 }

    inLines.forEach {
        var noCard = 0
        var noG = ""
        var noT = ""
        val match = patNum.find(it)
        if (match != null) {
            noCard = match.groups[1]!!.value.toInt()
            noG = match.groups[2]!!.value.trim()
            noT = match.groups[4]!!.value.trim()
        }
        val lstG = noG.split(" ")
        val lstT = noT.split(" ")

        tabCards[noCard] += 1
        var cp = 0
        lstT.forEach { num ->
            if (num.isNotEmpty()) {
                if (lstG.contains(num)) {
                    cp++
                }
            }
        }
        if (cp > 0) {
            for (i in noCard+1..noCard+cp) {
                tabCards[i] += tabCards[noCard]
            }
        }
    }

    println(tabCards.sum())
}

