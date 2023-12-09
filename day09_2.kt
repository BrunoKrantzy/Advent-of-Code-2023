
fun main() {
    //val inLines = readInput("test09_1")
    val inLines = readInput("input09_1")

    var lstValFin = mutableListOf<Long>()

    inLines.forEach {
        val lstLignes = mutableListOf<MutableList<Long>>()

        val lst = it.split(" ")
        val lstTemp = mutableListOf<Long>()
        lst.forEach { n ->
            lstTemp.add(n.toLong())
        }
        lstLignes.add(lstTemp.reversed() as MutableList) // seule dif. pour partie 2

        var posLigne = 0
        while (true) {
            val ligListe = lstLignes[posLigne]
            var newListe = mutableListOf<Long>()
            for (i in 0 until ligListe.size - 1) {
                val dif = ligListe[i+1] - ligListe[i]
                newListe.add(dif)
            }

            if (newListe.isNotEmpty()) {
                lstLignes.add(newListe)
                posLigne++
            }

            if (newListe.filter { it != 0L }.isEmpty())
                break
        }

        while (posLigne >= 1) {
            val ajoutVal = lstLignes[posLigne].last() + lstLignes[posLigne-1].last()
            lstLignes[posLigne-1].add(ajoutVal)
            posLigne--
        }
        lstValFin.add(lstLignes.first().last())
    }

    println(lstValFin.sum())
}

