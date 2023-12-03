fun main() {

    fun part1(input : List<String>) : Int {
        val numbers = input.listNumbers()
        val symbols = input.listSymbols()

        return numbers.filter{ it.isEngine(symbols, Number::isAdjacent) }.sumOf { it.value }
    }

    fun part2(input : List<String>) : Int  {
        val numbers = input.listNumbers()
        val symbols = input.listSymbols()

        return  symbols.map { it.isGear(numbers, Symbol::isAdjacent) }.filter { it.first }.sumOf { it.second }

    }

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}



fun Symbol.isGear(numbers : List<Number>, predicate: (Symbol, Number) -> Boolean) : Pair<Boolean, Int> {
    val adjs = numbers.filter { predicate(this, it) }
    return Pair(adjs.size == 2, adjs.fold(1){acc, num -> acc * num.value})
}
fun Number.isEngine(symbols: List<Symbol>, predicate: (Number, Symbol) -> Boolean) : Boolean {
    return symbols.any { predicate(this, it) }
}
fun Char.isSymbol() : Boolean {
    return !isDigit() && !equals('.')
}

fun List<String>.listSymbols() : List<Symbol> {
    val syms = mutableListOf<Symbol>()

    forEachIndexed { lineIdx, line ->
        var charIdx = 0

        while(charIdx <= line.length - 1) {
            if(line[charIdx].isSymbol()) {
                syms.add(Symbol(lineIdx, charIdx, line[charIdx]))
            }
            charIdx++
        }
    }

    return syms
}
fun List<String>.listNumbers() : List<Number> {
    val nums = mutableListOf<Number>()
    forEachIndexed { lineIndex, line ->
        var currentIndex = 0
        while (currentIndex < line.length - 1) {
            if (line[currentIndex].isDigit()) {
                val st = currentIndex
                while (currentIndex < line.length && line[currentIndex].isDigit()) {
                    currentIndex++
                }

                val end = currentIndex - 1

                nums.add(Number(lineIndex, st, end, value = Integer.parseInt(line.filterIndexed { index, _ -> index in st..end })))

            }
            currentIndex++
        }
    }

    return nums
}

data class Symbol(val line : Int, val indice : Int, val repre : Char) {
    fun isAdjacent(number: Number) : Boolean {
        return indice in number.startIndex - 1..number.endIndex + 1
                && line in number.line - 1 .. number.line + 1
    }
}
data class Number(val line : Int, val startIndex : Int, val endIndex :Int, val value : Int) {
    fun isAdjacent(symbol: Symbol) : Boolean {
        return symbol.indice in startIndex - 1..endIndex + 1
                && symbol.line in line - 1 .. line + 1
    }
}