
fun main() {
    val inLines: List<String>

    val bTest = false
    if (bTest) {
        inLines = readInput("test15_1")
    } else {
        inLines = readInput("input15_1")
    }

    fun calcHash(vIn:Int) : Int {
        var v = vIn * 17
        v %= 256
        return v
    }

    var rep = 0L
    val tabBoxes = Array(256) { mutableMapOf<String, Int>() }
    val lstBoxes = mutableListOf<MutableList<String>>()
    for (i in 0..255) {
        val sousListe = mutableListOf<String>()
        lstBoxes.add(sousListe)
    }

    val lstPat = inLines[0].split(",")
    lstPat.forEach {
        var label = ""
        var focal = -1
        var com = ' '
        if (it.contains("=")) {
            com = '='
            label = it.substringBefore("=")
            focal = it.substringAfter("=").toInt()
        }
        else {
            com = '-'
            label = it.substringBefore("-")
        }

        val mapLabelHash = mutableMapOf<String, Int>()
        var hashLabel = -1
        if (mapLabelHash.containsKey(label))
            hashLabel = mapLabelHash[label]!!
        else {
            var vt = 0
            label.forEach { car ->
                var v = car.code
                v += vt
                vt = calcHash(v)
            }
            mapLabelHash[label] = vt
            hashLabel = mapLabelHash[label]!!
        }

        if (com == '=') {
            // présence dans la box ?
            if (tabBoxes[hashLabel].containsKey(label)) {
                tabBoxes[hashLabel].remove(label)
                tabBoxes[hashLabel][label]=focal
            }
            else {
                tabBoxes[hashLabel][label]=focal
                lstBoxes[hashLabel].add(label)
            }
        }
        else {
            if (tabBoxes[hashLabel].containsKey(label)) {
                tabBoxes[hashLabel].remove(label)
                lstBoxes[hashLabel].remove(label)
            }
        }
    }

    // calcul final
    tabBoxes.forEachIndexed { idxTab, mMap ->
        lstBoxes[idxTab].forEachIndexed { idxLst, label ->
            var slot = idxLst + 1
            var focal = tabBoxes[idxTab][label]
            var v = (1 + idxTab) * slot * focal!!
            rep += v.toLong()
        }
    }

    println(rep)
}
