import kotlin.IllegalArgumentException

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            sum += line.getDigits()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            sum += line.getParsedDigits()
        }

        return sum
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()



}


private fun String.getDigits() : Int {
    var tmp = this.filter { it.isDigit() }

    if (tmp.length == 1) {
        tmp = tmp.padEnd(2, tmp[0])
    }

    val last = tmp.lastIndex
    val cs = tmp.filterIndexed { index, _ ->  index == 0 || index == last}
    return Integer.parseInt(cs)
}

private fun String.getParsedDigits(): Int {
    val first = parseFirstDigit(this, writtenDigitsList())
    val last = parseFirstDigit(this.reversed(), writtenDigitsList().map { it.reversed() })
    return Integer.parseInt(first+last)
}

private fun parseFirstDigit(s: String, digitsList: List<String>): String {
    return when {
        s.startsWithDigit() -> {
            s[0].toString()
        }
        s.startsWithWrittenDigit(digitsList).first -> {
            parseWrittenDigit(s.dropLast(s.length - s.startsWithWrittenDigit(digitsList).second), digitsList)
        }
        else -> parseFirstDigit(s.drop(1),digitsList)
    }

}
private fun String.startsWithDigit(): Boolean {
    return this[0].isDigit()
}

private fun String.startsWithWrittenDigit(digitsList: List<String>): Pair<Boolean, Int> {

    for (digit in digitsList) {
        if (this.startsWith(digit)) {
            return Pair(true, digit.length)
        }
    }
    return Pair(false,0)
}


private fun writtenDigitsList() : List<String> {
    return listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
}


fun parseWrittenDigit(s: String, l : List<String>): String {
    for (i in l) {
        if (s == i) {
            return l.indexOf(i).toString()
        }
    }
    throw IllegalArgumentException()
}


