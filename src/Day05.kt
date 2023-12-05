

fun Long.passThrough(maps : List<Map<Long, Long>>) : Long {
    var ret = this
    maps.forEach { map ->
        ret = map.getOrDefault(ret, ret)
    }
    return ret
}
fun main() {
    fun part1(parsed : Parsed) : Long {
        return  parsed.seeds.map { it.passThrough(parsed.maps) }.minOf { it }
    }

    fun part2(input : Parsed) {

    }

    val input = readInput("Day05")

    val parsed = parseInput(input)
    parsed.println()

    val p1 = part1(parsed)
    p1.println()
}

fun String.parseWith(start: String) : List<Long> {
    return this.substringAfter(start).parseLine()
}

fun String.parseLine() : List<Long> {
    return this.split(" ").filter { it.isNotBlank() }.map { it.toLong()}
}

fun categories() : List<String> {
    return listOf("seeds:", "seed-to-soil map:", "soil-to-fertilizer map:", "fertilizer-to-water map:",
        "water-to-light map:", "light-to-temperature map:", "temperature-to-humidity map:",
        "humidity-to-location map:")
}


fun List<Long>.toRangeTriple() : Triple<Long, Long, Long> {
    return if (this.size == 3) Triple(this[0], this[1], this[2]) else throw IllegalArgumentException("Bad Format")
}
fun List<Long>.toRange() : List<Pair<Long, Long>> {
    if(this.size != 3) throw IllegalArgumentException("Bad Format")
    val dest = this[0]
    val source = this[1]
    val range = this[2]

    val li = mutableListOf<Pair<Long,Long>>()
    for (it in source until source+range) {
        li.add(Pair(it, dest + (it - source)))
    }
    return li
}

fun parseInput(input : List<String>) : Parsed {
    var index = 0
    val seeds =  input[index].parseWith(categories()[index])

    val maps : MutableList<Map<Long, Long>> = mutableListOf()

    for (cat in categories().drop(1)) {
        val map : MutableMap<Long, Long> = mutableMapOf()
        while(input[index++] != cat);

        while(index in input.indices && input[index].isNotBlank()) {
            if (index !in input.indices) {
                break
            }
            val line = input[index].parseLine()
            //val range = line.toRange()
            val range = line.toRangeTriple()

            for (value in range.first..range.first+range.third) {
               map[value] = range.second + value - range.first
            }
            /*range.forEach {
                map[it.first] = it.second
            }*/
            index++
        }
        maps.add(map.toMap())
    }

    return Parsed(seeds, maps.toList())
}


data class Parsed(val seeds : List<Long>, val maps : List<Map<Long, Long>>)