import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

private fun readChars() = readLn().toCharArray()
private fun readLn() = readln() // string line
private fun readSb() = StringBuilder(readLn())
private fun readInt() = readLn().toInt() // single int
private fun readLong() = readLn().toLong() // single long
private fun readBigInt() = readLn().toBigInteger() // single BigInteger
private fun readDouble() = readLn().toDouble() // single double
private fun readStrings() = readLn().split(" ") // list of strings
private fun readInts() = readStrings().map { it.toInt() } // list of ints
private fun readLongs() = readStrings().map { it.toLong() } // list of longs
private fun readBigInts() = readStrings().map { it.toBigInteger() } // list of BigIntegers
private fun readDoubles() = readStrings().map { it.toDouble() } // list of doubles
private fun readIntArray() = readStrings().map { it.toInt() }.toIntArray() // Array of ints
private fun readLongArray() = readStrings().map { it.toLong() }.toLongArray() // Array of longs
private fun readMutableMap() = readLn().groupingBy { it }.eachCount().toMutableMap() // map char/Int occur.


fun readInput(name: String) = File("src", "$name.txt").readLines()

class MutableStack<E>(vararg items: E) {
    private val elements = items.toMutableList()
    fun push(element: E) = elements.add(element)
    fun peek(): E = elements.last()
    fun pop(): E = elements.removeAt(elements.size - 1)
    fun isEmpty() = elements.isEmpty()
    fun size() = elements.size
    override fun toString() = elements.joinToString(" ")
}

data class Directory (val nomDir: Int, var parent: Int, var tailleDir:Long)


/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun String.md5pad() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


// Ctrl + Space -> code completion
// Ctrl + P -> paramètres

// val tab2D: Array<Array<Int>> = Array (5) { Array(3) { 0 } }

// val tabA = IntArray(100) { it + 1 }

// val dp = Array(100 + 1) { Array(100 + 1) { BooleanArray(10000 + 1) } }

// val tabI = readInts().toIntArray()
// val tabC = readLn().toCharArray()

// val liste = readLn().toList().sorted()

// tableau 2D de n Strings
// var matrix = Array(n) { readLn() }

// var tabListes: Array<MutableList<Int>> = Array (m) { mutableListOf() }

// var tabDataClasse: Array<DataClasse> = Array (5) { DataClasse(0, 0, 0) }

// pour une lecture d'éléments à indexer
// val (l, r) = readLn().map { it.toInt() - 1 }

// pour une lecture d'éléments à indexer avec abandon de la première valeur lue
// val days = readInts().drop(1).map { it - 1 } as MutableList<Int>

// println(list.joinToString("\n")) // each element of array/list with separate line
// println(list.joinToString(" "))  // each element of array/list with separate space

// un StringBuilder java qui prévoit le séparateur
// val sj = StringJoiner(" ")

// utiliser asSequence lors de traitement de données
// (1 until 2_000_000).asSequence()
// .map { un traitement }
// .filter { un filtrage }
// .first()

// val MOD = 1_000_000_007

// private fun Int.pow(x: Int) = toDouble().pow(x).toInt()
// private fun Long.pow(x: Int) = toDouble().pow(x).toLong()

