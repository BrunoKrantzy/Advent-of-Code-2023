
fun main() {

    val inLines = readInput("input01_1")
    //val inLines = readInput("test01_1")

    var rep = 0

    val lstD = mutableListOf("@$@$", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    val lstNewLines = mutableListOf<String>()
    var line = ""
    val lstPos = mutableListOf<Pair<Int, String>>()

    inLines.forEach {
        line = it.toString()
        for (i in 1 until lstD.size) {
            val num = lstD[i]
            if (line.contains(num)) {
                var pos = line.indexOf(num, 0)
                while (pos != -1) {
                    var pPos = Pair(pos, num)
                    lstPos.add(pPos)
                    pos = line.indexOf(num, pos+num.length)
                }
            }
        }
        lstPos.sortBy { it.first }

        if (lstPos.isNotEmpty()) {
            val first = lstPos.first().second
            var last = ""
            if (lstPos.size > 1) {
                last = lstPos.last().second
            }

            if (line.contains(first)) {
                line = line.replaceFirst(first, lstD.indexOf(first).toString() + first)
            }
            if (last.isNotBlank() && line.contains(last)) {
                var lineR = line.reversed()
                val lastR = last.reversed()
                lineR = lineR.replaceFirst(lastR, lstD.indexOf(last).toString() + last)
                line = lineR.reversed()
            }
        }
        lstNewLines.add(line)
        lstPos.clear()
    }

    lstNewLines.forEach {
        val f = it.first { c -> c.isDigit() }
        val l = it.last { c -> c.isDigit() }

        val n = ("$f" + "$l").toInt()
        println(n)
        rep += n
    }

    println(rep)
}

