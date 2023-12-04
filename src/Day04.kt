import kotlin.math.pow

fun main() {
    fun part1(cards : List<Card>) : Int {
        return cards.sumOf { it.countPoints() }
    }

    fun part2(cards : List<Card>) : Int  {
        return cards.expandOpt().sumOf { it.second }
    }

    val input = readInput("Day04")
    val cards = listCards(input)
    println(part1(cards))
    println(part2(cards))

}

fun listCards(input : List<String>) : List<Card> {
    val cards = mutableListOf<Card>()

    for (line in input) {
        val spl = line.split(":", "|")
        val id = Integer.parseInt(spl[0].substringAfter("Card ").trim())

        val winnings = spl[1].split(" ").filter{ it.isNotBlank()}.map { it.trim() }.map { Integer.parseInt(it) }

        val obtained = spl[2].split(" ").filter {  it.isNotBlank() }.map { it.trim() }.map { Integer.parseInt(it) }

        cards.add(Card(id, winnings, obtained))
    }
    return cards
}


fun List<Card>.expandOpt() : MutableList<Pair<Card, Int>> {
    var numberOfCards : MutableList<Pair<Card, Int>> = this.map { Pair(it, 1) }.toMutableList()

    val gains = mutableListOf<List<Card>>()

    for (card in this) {
        gains.add(this.filter { c -> c.id in card.id + 1..card.id + card.countMatching() })
    }

    this.forEachIndexed  { index, card ->
        val cardCount = numberOfCards.first { it.first == card }.second
        numberOfCards = numberOfCards.map { if(it.first in gains[index]) Pair(it.first, it.second + cardCount) else it }.toMutableList()
    }
    return numberOfCards

}
data class Card(val id : Int, val winnings : List<Int>, val obtained : List<Int>) {

    fun countMatching() : Int {
        return  winnings.filter { obtained.contains(it) }.size
    }
    fun countPoints() : Int {
        return when(val count = countMatching()) {
            0 -> 0
            else -> 2.0.pow(count - 1).toInt()
        }
    }
}