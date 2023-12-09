

fun main() {
    fun part1(series : List<Serie>) : Long {
        return series.map { it.nextVal() }.sumOf { it }
    }

    fun part2(series : List<Serie>) : Long {
        return series.map { it.previousVal() }.sumOf { it }
    }

    val input = readInput("Day09")
    part1(input.toSerie()).println()
    part2(input.toSerie()).println()
}

fun List<String>.toSerie() : List<Serie> {
    return this.map { it.split(" ") }.map { line -> line.map { Integer.parseInt(it).toLong() }}.map { Serie(it) }.toList()
}


typealias FirstValues = List<Long>

typealias ActualList = List<Long>

enum class Position {
    FIRST,
    LAST
}
data class Serie(var baseList : List<Long>) {
    fun nextVal() : Long {
        return reconstruct(deconstruct(Position.FIRST)).last()
    }

    fun previousVal() : Long {
        return getPrevious(deconstruct(Position.LAST))
    }
    private fun deconstruct(position: Position) : Pair<FirstValues, ActualList> {
        val firstValues = mutableListOf<Long>()

        while(true) {
            val li : MutableList<Long> = mutableListOf()

            firstValues.add(baseList.first())

            baseList.forEachIndexed { index, el -> if(index != baseList.size-1) li.add(baseList[index+1] - el) }

            if(li.all { it == 0.toLong() }) {

                when(position) {
                    Position.FIRST -> li.add(0)
                    Position.LAST -> li.add(li.lastIndex+1,0)
                }
            }

            baseList = li
            if(li.all { it == 0.toLong() }) {
                break
            }

        }

        return Pair(firstValues.toList(), baseList)
    }


    private fun reconstruct(data : Pair<FirstValues, ActualList>) : List<Long> {
        var li = data.second

        for (first in data.first.reversed()) {
            li = li.scan(first)  {acc , el -> acc + el}
        }

        return li.toList()
    }

    private fun getPrevious(data: Pair<FirstValues, ActualList>) : Long {
        var tt = data.first.drop(1).last()
        for (fistValue in data.first.reversed().drop(1)) {
            tt = fistValue - tt
        }
        return tt
    }
}