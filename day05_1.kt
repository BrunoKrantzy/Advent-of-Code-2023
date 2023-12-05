
data class DatasZones (var dest: Long, var source: Long, var range: Long)

fun main() {

    val inLines = readInput("input05_1")
    //val inLines = readInput("test05_1")

    /*
    seeds: ...

    seed-to-soil map:           1
    soil-to-fertilizer map:     2
    fertilizer-to-water map:    3
    water-to-light map:         4
    light-to-temperature map:   5
    temperature-to-humidity map:6
    humidity-to-location map:   7
    */

    var patSeeds = "(seeds:) ([\\d+ *]+)".toRegex()
    var patDatas = "(\\d+) +(\\d+) +(\\d+)".toRegex()

    var zone = 0 // zone des datas en cours
    var lstOriginalSeeds = mutableListOf<Long>()
    var lstSeedsLoc = mutableListOf<Pair<Long, Long>>()

    var mapSeedSoil = mutableMapOf<Long, Long>()
    var mapSoilFertil = mutableMapOf<Long, Long>()
    var mapFertilWater = mutableMapOf<Long, Long>()
    var mapWaterLight = mutableMapOf<Long, Long>()
    var mapLightTemp = mutableMapOf<Long, Long>()
    var mapTempHumid = mutableMapOf<Long, Long>()
    var mapHumidLoc = mutableMapOf<Long, Long>()
    var mapSeedLoc = mutableMapOf<Long, Long>()

    var datasZone1 = mutableListOf<DatasZones>()
    var datasZone2 = mutableListOf<DatasZones>()
    var datasZone3 = mutableListOf<DatasZones>()
    var datasZone4 = mutableListOf<DatasZones>()
    var datasZone5 = mutableListOf<DatasZones>()
    var datasZone6 = mutableListOf<DatasZones>()
    var datasZone7 = mutableListOf<DatasZones>()

    for (nLine in 0 until inLines.size) {
        val lineStr = inLines[nLine]
        val matchSeeds = patSeeds.find(lineStr)
        if (matchSeeds != null) {
            val blocSeeds = matchSeeds.groups[2]!!.value
            var lst = blocSeeds.split(" ")
            lst.forEach {
                lstOriginalSeeds.add(it.toLong())
            }
        }
        else if (lineStr.contains("seed-to-soil map:"))
            zone = 1
        else if (lineStr.contains("soil-to-fertilizer map:"))
            zone = 2
        else if (lineStr.contains("fertilizer-to-water map:"))
            zone = 3
        else if (lineStr.contains("water-to-light map:"))
            zone = 4
        else if (lineStr.contains("light-to-temperature map:"))
            zone = 5
        else if (lineStr.contains("temperature-to-humidity map:"))
            zone = 6
        else if (lineStr.contains("humidity-to-location map:"))
            zone = 7
        else if (patDatas.find(lineStr) != null) {
            val matchDatas = patDatas.find(lineStr)
            if (matchDatas != null) {
                var noDest = matchDatas.groups[1]!!.value.toLong()
                var noSource = matchDatas.groups[2]!!.value.toLong()
                var noRange = matchDatas.groups[3]!!.value.toLong()

                var liDatas = DatasZones(noDest, noSource, noRange)

                when (zone) {
                    1 -> {
                        datasZone1.add(liDatas)
                    }
                    2 -> {
                        datasZone2.add(liDatas)
                    }
                    3 -> {
                        datasZone3.add(liDatas)
                    }
                    4 -> {
                        datasZone4.add(liDatas)
                    }
                    5 -> {
                        datasZone5.add(liDatas)
                    }
                    6 -> {
                        datasZone6.add(liDatas)
                    }
                    7 -> {
                        datasZone7.add(liDatas)
                    }
                }
            }
        }
        else if (lineStr.isNotBlank()) {
            println("ERREUR")
            println(lineStr)
        }
    }

    fun trtZones (lstZone: MutableList<DatasZones>, numSource: Long ) : Pair<Long, Long> {
        var ligneMap = Pair(0L, 0L)

        for (i in 0 until lstZone.size) {
            var rang = -1L
            var datas = lstZone[i]
            if (numSource >= datas.source && numSource <= datas.source + (datas.range - 1)) {
                rang = numSource - datas.source
                val numType = datas.dest + rang
                ligneMap = Pair(numSource, numType)
                break
            }
        }
        return ligneMap
    }

    lstOriginalSeeds.forEach {
        val numType = it
        val ligMap = trtZones(datasZone1, numType)
        if (ligMap.second == 0L) {
            mapSeedSoil[numType] = numType
        }
        else {
            mapSeedSoil[ligMap.first] = ligMap.second
        }
    }

    mapSeedSoil.forEach {
        val numType = it.value
        val ligMap = trtZones(datasZone2, numType)
        if (ligMap.second == 0L) {
            mapSoilFertil[numType] = numType
        }
        else {
            mapSoilFertil[ligMap.first] = ligMap.second
        }
    }

    mapSoilFertil.forEach {
        val numType = it.value
        val ligMap = trtZones(datasZone3, numType)
        if (ligMap.second == 0L) {
            mapFertilWater[numType] = numType
        }
        else {
            mapFertilWater[ligMap.first] = ligMap.second
        }
    }

    mapFertilWater.forEach {
        val numType = it.value
        val ligMap = trtZones(datasZone4, numType)
        if (ligMap.second == 0L) {
            mapWaterLight[numType] = numType
        }
        else {
            mapWaterLight[ligMap.first] = ligMap.second
        }
    }

    mapWaterLight.forEach {
        val numType = it.value
        val ligMap = trtZones(datasZone5, numType)
        if (ligMap.second == 0L) {
            mapLightTemp[numType] = numType
        }
        else {
            mapLightTemp[ligMap.first] = ligMap.second
        }
    }

    mapLightTemp.forEach {
        val numType = it.value
        val ligMap = trtZones(datasZone6, numType)
        if (ligMap.second == 0L) {
            mapTempHumid[numType] = numType
        }
        else {
            mapTempHumid[ligMap.first] = ligMap.second
        }
    }

    mapTempHumid.forEach {
        val numType = it.value
        val ligMap = trtZones(datasZone7, numType)
        if (ligMap.second == 0L) {
            mapHumidLoc[numType] = numType
        }
        else {
            mapHumidLoc[ligMap.first] = ligMap.second
        }
    }

    for (seed in lstOriginalSeeds) {
        var seedLoc = 0L
        val soil = mapSeedSoil[seed]
        val fertil = mapSoilFertil[soil]
        val water = mapFertilWater[fertil]
        val light = mapWaterLight[water]
        val temp = mapLightTemp[light]
        val humid = mapTempHumid[temp]
        seedLoc = mapHumidLoc[humid]!!

        lstSeedsLoc.add(Pair(seed, seedLoc))
        mapSeedLoc[seed] = seedLoc
    }

    var minLoc = Long.MAX_VALUE
    lstSeedsLoc.forEach {
        minLoc = minOf(minLoc, it.second)
    }

    println(minLoc)
}

