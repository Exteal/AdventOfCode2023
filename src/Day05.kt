

typealias Range = Triple<Long, Long, Long>
fun Long.passThrough(maps : List<List<Range>>) : Long {
    var ret = this
    maps.forEach Out@ { list ->
        list.forEach Depth@ {
            if (ret in it.first..it.first + it.third) {
                val diff = ret - it.first
                ret = it.second + diff
                return@Out
            }
        }
    }
    return ret
}
fun main() {
    fun part1(parsed : ParsedOpt) : Long {
        return parsed.seeds.map { it.passThrough(parsed.maps) }.minOf { it }
    }

    fun part2(parsed : ParsedOpt) : Long {
        return parsed.seeds.map { it.passThrough(parsed.maps) }.minOf { it }
    }

    val input = readInput("Day05")

    part1(parseInput(input, method = ::parseSeedsDirect)).println()
    part2(parseInput(input, method = ::parseSeedsByRange)).println()
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


fun List<Long>.toRangeTriple() : Range {
    return if (this.size == 3) Triple(this[1], this[0], this[2]) else throw IllegalArgumentException("Bad Format")
}


fun parseSeedsDirect(input : List<String>) : List<Long> {
    return input[0].parseWith(categories()[0])
}

fun parseSeedsByRange(input : List<String>) : List<Long> {
    val vals = input[0].parseWith(categories()[0])
    val seeds : MutableList<Long> = mutableListOf()
    for (idx in vals.indices step 2) {
        seeds.addAll(vals[idx].rangeTo(vals[idx+1]))
    }
    return seeds
}
fun parseInput(input : List<String>, method : (List<String>) -> List<Long>) : ParsedOpt {
    var index = 0
    val seeds =  method(input)
    val maps : MutableList<List<Range>> = mutableListOf()

    for (cat in categories().drop(1)) {
        val map : MutableList<Range> = mutableListOf()

        while(input[index++] != cat);

        while(index in input.indices && input[index].isNotBlank()) {
            if (index !in input.indices) {
                break
            }
            val line = input[index].parseLine()
            val range = line.toRangeTriple()

            map.add(range)
            index++
        }
        maps.add(map.toList())
    }
    return ParsedOpt(seeds, maps.toList())

}


data class ParsedOpt(val seeds : List<Long>, val maps : List<List<Range>>)