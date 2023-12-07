
fun main() {

    //val inLines = readInput("test07_1")
    val inLines = readInput("input07_1")

    val mapValeur = mutableMapOf<Char, Char>()
    mapValeur['A'] = 'A'
    mapValeur['K'] = 'B'
    mapValeur['Q'] = 'C'
    mapValeur['J'] = 'Z' // classé dernier
    mapValeur['T'] = 'E'
    mapValeur['9'] = 'F'
    mapValeur['8'] = 'G'
    mapValeur['7'] = 'H'
    mapValeur['6'] = 'I'
    mapValeur['5'] = 'J'
    mapValeur['4'] = 'K'
    mapValeur['3'] = 'L'
    mapValeur['2'] = 'M'

    data class Handclass(val type:String, val hand:String, val key:String, var bid:Long)

    val lstHands = mutableListOf<Handclass>()
    inLines.forEach {
        val h = it.substringBefore(' ')
        val b = it.substringAfter(' ')

        var type = ""
        val mapCards = mutableMapOf<Char, Int>()
        val key = StringBuilder()
        h.forEach { c ->
            key.append(mapValeur[c])
            if (mapCards.containsKey(c))
                mapCards[c] = mapCards[c]!!.plus(1)
            else
                mapCards[c] = 1
        }
        val sz = mapCards.size
        when (sz) {
            1 -> type = "G"
            2 -> {
                if (mapCards.containsKey('J')) {
                    type = "G"
                }
                else if (mapCards.containsValue(4))
                    type = "F"
                else
                    type = "E"
            }
            3 -> {
                if (mapCards.containsKey('J')) {
                    if (mapCards['J']!! >= 2)
                        type = "F"
                    else if (mapCards.containsValue(3))
                        type = "F"
                    else
                        type = "E"
                }
                else if (mapCards.containsValue(3))
                    type = "D"
                else
                    type = "C"
            }
            4 -> {
                if (mapCards.containsKey('J')) {
                    type = "D"
                }
                else
                    type = "B"
            }
            5 -> {
                if (mapCards.containsKey('J')) {
                    type = "B"
                }
                else
                    type = "A"
            }
        }

        val hc = Handclass(type, h, key.toString(), b.toLong())
        lstHands.add(hc)
    }

    val comparator = compareBy<Handclass> { it.type }
        .thenByDescending { it.key }
    val lstGlobale = lstHands.sortedWith(comparator)

    var rep = 0L
    var pos = 0
    lstGlobale.forEach {
        pos++
        rep += pos * it.bid
    }

    println(rep)
}

