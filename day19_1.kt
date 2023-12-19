
fun main() {
    val inLines: List<String>

    val bTest = false
    inLines = if (bTest) {
        readInput("test19_1")
    } else {
        readInput("input19_1")
    }

    // px{a<2006:qkq,m>2090:A,rfg}
    var patNom = "^([a-z]+)\\{(.+)}".toRegex()

    data class Rule(val categ:String, val clause:Char, val valClause:Int, val dest:String)
    var mapFlows: MutableMap<String, ArrayDeque<Rule>> = mutableMapOf()

    data class Part(var mapCateg:MutableMap<String, Int>)
    var lstParts = mutableListOf<Part>()
    var lstPartAccepted = mutableListOf<Part>()

    var inP1 = true
    inLines.forEach {
        if (it.isBlank()) inP1 = false
        if (inP1) {
            var stack: ArrayDeque<Rule> = ArrayDeque()
            var match = patNom.find(it)
            var nameFlow = match!!.groups[1]!!.value
            var rules = match.groups[2]!!.value
            var lstRules = rules.split(",")
            var nbRules = lstRules.size
            for (i in 0 until nbRules-1) {
                var l = lstRules[i]
                var part = l[0].toString()
                var clause = l[1]
                l = l.removeRange(0..1)
                var valClause = l.substringBefore(":").toInt()
                var dest = l.substringAfter(":")

                var dataPart = Rule(part, clause, valClause, dest)
                stack.add(dataPart)
            }
            var lastDest = lstRules.last()
            val dP = Rule(lastDest, '=', -1, lastDest.toString() )
            stack.add(dP)
            mapFlows[nameFlow] = stack
        }

        // {x=787,m=2655,a=1222,s=2876}
        if (!inP1 && it.isNotBlank()) {
            var str = it
            str = str.removePrefix("{")
            str = str.removeSuffix("}")
            var lstCateg = str.split(",")
            var mapCateg = mutableMapOf<String, Int>()
            lstCateg.forEach {
                val categ = it.substringBefore("=")
                val rate = it.substringAfter("=")
                mapCateg[categ] = rate.toInt()
            }
            val part = Part(mapCateg)
            lstParts.add(part)
        }
    }

    lstParts.forEach { part ->
        var finalFlow = "in"
        while (finalFlow != "A" && finalFlow != "R") {
            var rulesIn = mapFlows[finalFlow]
            var nbRules = rulesIn!!.size
            for (i in 0 until nbRules) {
                val rule = rulesIn[i]
                val categRule = rule.categ
                val categClause = rule.clause
                val categValClause = rule.valClause
                val categDest = rule.dest
                if (categValClause == -1) {
                    finalFlow = categDest
                    break
                }
                val partValClause = part.mapCateg[categRule]
                var ruleValide = false
                when (categClause) {
                    '>' -> {
                        if (partValClause!! > categValClause)
                            ruleValide = true
                    }
                    '<' -> {
                        if (partValClause!! < categValClause)
                            ruleValide = true
                    }
                }
                if (ruleValide) {
                    finalFlow = categDest
                    break
                }
            }
        }
        if (finalFlow == "A")
            lstPartAccepted.add(part)
    }

    var rep = 0L
    lstPartAccepted.forEach { part ->
        rep += part.mapCateg["x"]!!
        rep += part.mapCateg["m"]!!
        rep += part.mapCateg["a"]!!
        rep += part.mapCateg["s"]!!
    }

    println(rep)
}

