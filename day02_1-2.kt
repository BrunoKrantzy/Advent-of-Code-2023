// parties 1 et 2

fun main() {

    val inLines = readInput("input02_1")
    //val inLines = readInput("test02_1")

    var rep = 0 // partie 1
    var powerGame = 0L // partie 2

    val patGame ="Game (\\d+): (.*)".toRegex()
    val patColor = "(\\d+) ([a-z]+)".toRegex()

    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    inLines.forEach {
        val line = "$it;"
        var goodGame = true

        var nbRed = 0
        var nbGreen = 0
        var nbBlue = 0

        var minRed = 0
        var minGreen = 0
        var minBlue = 0

        val matchGame = patGame.find(line)
        if (matchGame != null) {
            val noGame = matchGame.groups[1]!!.value.toInt()
            val strGame = matchGame.groups[2]!!.value

            val lstSets = strGame.split(";")
            lstSets.forEach { itSet ->

                val lstColors = itSet.split(",")
                lstColors.forEach { itColors ->
                    val strColor = patColor.find(itColors)
                    if (strColor != null) {
                        val nbCubes = strColor.groups[1]!!.value.toInt()
                        val color = strColor.groups[2]!!.value
                        when (color) {
                            "green" -> {
                                nbGreen += nbCubes
                                minGreen = maxOf(minGreen, nbCubes)
                            }
                            "red" -> {
                                nbRed += nbCubes
                                minRed = maxOf(minRed, nbCubes)
                            }
                            "blue" -> {
                                nbBlue += nbCubes
                                minBlue = maxOf(minBlue, nbCubes)
                            }
                        }


                        if (nbGreen > maxGreen || nbRed > maxRed || nbBlue > maxBlue) {
                            goodGame = false
                        }
                    }
                }
            }

            if (goodGame) {
                rep += noGame
            }

            powerGame += (minBlue * minRed * minGreen)
        }
    }

    println("Partie 1 : $rep")
    println("Partie 2 : $powerGame")
}

