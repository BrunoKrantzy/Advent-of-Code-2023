
import org.jgrapht.Graph
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

fun main() {
    val inLines: List<String>

    val bTest = false
    inLines = if (bTest) {
        readInput("test25_1")
    } else {
        readInput("input25_1")
    }

    val patLine = "([a-z]+): ([[a-z]+\\s?]+)".toRegex()
    val mapCodes = mutableMapOf<String, MutableList<String>>()

    inLines.forEach {
        var premCode = ""
        var match = patLine.find(it)
        if (match != null) {
            premCode = match.groups[1]!!.value
            val suite = match.groups[2]!!.value
            var lstSuite = suite.split(" ") as MutableList

            if (!mapCodes.containsKey(premCode)) {
                mapCodes[premCode] = mutableListOf()
            }

            lstSuite.forEach { code ->
                mapCodes[premCode]!!.add(code)

                if (mapCodes.containsKey(code))
                    mapCodes[code]!!.add(premCode)
                else {
                    mapCodes[code] = mutableListOf()
                    mapCodes[code]!!.add(premCode)
                }
            }
        }
    }

    fun createStringGraph(): Graph<String, DefaultEdge> {
        val g: Graph<String, DefaultEdge> = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)

        mapCodes.forEach { key ->
           g.addVertex(key.key)
        }

        mapCodes.forEach {
            var v1 = it.key
            for (v2 in it.value) {
                g.addEdge(v1, v2)
            }
        }

        return g
    }

    val graph = createStringGraph()
    val group1 = StoerWagnerMinimumCut(graph).minCut()

    val group2 = mapCodes.size - group1.size
    println(group1.size * group2)
}


