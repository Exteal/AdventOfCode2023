import kotlin.math.abs

fun main() {
    readInput("Day11").expand(factor = 2).toGalaxies().part1().println()
    readInput("Day11").expand(factor = 1000000).toGalaxies().part2().println()
}

fun List<Galaxy>.part1() : Int {
    return this.map {
        gal -> this.fold(0) { acc: Int, galaxy: Galaxy -> acc + (if (galaxy != gal)  gal.difference(galaxy) else 0) }
    }.sumOf { it }.div(2)
}

fun List<Galaxy>.part2() : Int {
    return this.map {
            gal -> this.fold(0) { acc: Int, galaxy: Galaxy -> acc + (if (galaxy != gal)  gal.difference(galaxy) else 0) }
    }.sumOf { it }.div(2)
}

fun List<String>.expand(factor : Int) : List<String> {
    return this.expandLines(factor).expandCols(factor)
}

fun List<String>.expandLines(factor: Int) : List<String> {
    val ret = mutableListOf<String>()
    val regex : Regex = StringBuilder().append("[").append(Representations.getSpaceRepresentation()).append("]").append('*').toString().toRegex()
    this.forEach {
        if (regex.matches(it)) {
            for( x in 0..factor) {
                ret.add(it)
            }
        }
        else ret.add(it)
    }
    return ret.toList()
}

fun List<String>.expandCols(factor: Int) : List<String> {
    val ret = mutableListOf<String>()
    val regex : Regex = StringBuilder().append("[").append(Representations.getSpaceRepresentation()).append("]").append('*').toString().toRegex()

    for ( i in 0..<this[0].length) {
        val col = StringBuilder()
        this.map {  it[i] }.forEach { col.append(it) }
        val colx = col.toString()
        if (regex.matches(col)) {
            for( x in 0..factor) {
                ret.add(colx)
            }
        }
        else ret.add(colx)
    }
    return ret.toList()
}

fun List<String>.toGalaxies() : List<Galaxy> {
    val galaxies = mutableListOf<Galaxy>()
    this.forEachIndexed { idL, str -> str.forEachIndexed { idC, chr -> if(chr == Representations.getGalaxyRepresentation()) galaxies.add(Galaxy(Coordinates(idL, idC)))}}
    return galaxies.toList()
}

typealias Coordinates = Pair<Int, Int>


object Representations {
    fun getGalaxyRepresentation() : Char{
        return '#'
    }

    fun getSpaceRepresentation() : Char {
        return '.'
    }
}
data class Galaxy(val coordinates : Coordinates) {
    companion object Representation {

    }

    fun difference(galaxy: Galaxy) : Int {
        return abs(coordinates.first - galaxy.coordinates.first)  + abs(coordinates.second - galaxy.coordinates.second)
    }
}