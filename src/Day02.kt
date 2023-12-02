import kotlin.math.max

fun main() {

    fun part1(input : List<String>) : Int {
        return computeSum(input, predicate = GameData::isValid, method = GameData::getId)
    }

    fun part2(input : List<String>) : Int {
      return computeSum(input, predicate = {_ : GameData -> true}, method = GameData::setPower)
    }

    val input = readInput("Day02")


    println(part1(input))
    println(part2(input))
}


fun computeSum(input: List<String>, predicate: (GameData) -> Boolean, method: (GameData) -> Int) : Int {
    var sum = 0
    for (game in input) {

        val data =  game.split(':', ';')
        val gameData = GameData(Integer.parseInt( data[0].filter { c -> c.isDigit() }))

        val counts = data.drop(1)

        counts.forEach {m -> m.split(",").forEach{
            when {
                it.contains("red") -> {gameData.updateInfo(it, Colors.Red)}
                it.contains("green") -> {gameData.updateInfo(it, Colors.Green)}
                it.contains("blue") -> {gameData.updateInfo(it, Colors.Blue)}
            }
        }}
        if(predicate(gameData)) {
            sum += method(gameData)
        }

    }
    return sum

}
enum class Colors {
    Red,
    Green,
    Blue
}
class GameData(private val id: Int) {
    private var redCount = 0
    private var blueCount = 0
    private var greenCount = 0

    companion object ErrorData {
        private const val redMax = 12
        private const val greenMax = 13
        private const val blueMax = 14

        fun hasError(gameData: GameData) : Boolean{
            return gameData.redCount > redMax || gameData.greenCount > greenMax || gameData.blueCount > blueMax
        }
    }

    fun getId() : Int {
        return id
    }
    fun updateInfo(info: String, color : Colors) {
       val count =  Integer.parseInt( info.filter { c -> c.isDigit() })
        when(color) {
            Colors.Red -> { redCount = max(redCount, count) }
            Colors.Green -> { greenCount = max(greenCount, count) }
            Colors.Blue -> { blueCount = max(blueCount, count) }
        }

    }

    override fun toString(): String {
        return "red :$redCount green :$greenCount blue : $blueCount \n"
    }

    fun setPower() : Int {
        return redCount * greenCount * blueCount
    }

    fun isValid(): Boolean {
        return !hasError(this)
    }
}
