data class MinesweeperBoard(val board: List<String>) {

    private val numberedBoard: List<List<Field>>
    private val width = board.firstOrNull()?.length ?: 0
    private val height = board.size

    init {
        numberedBoard = board.toNumberedBoard()
    }

    private fun List<String>.toNumberedBoard() = toFields().apply {
        forEachIndexed { y, line ->
            line.forEachIndexed { x, currentField ->
                if (currentField is MineField) {
                    increaseMineCountOfAdjacentFields(x, y)
                }
            }
        }
    }

    private fun List<String>.toFields() = mapIndexed { y, line -> line.toFields(y).toMutableList() }.toMutableList()

    private fun String.toFields(y: Int) = mapIndexed { x, char -> if (char == MINE_CHAR) MineField(x, y) else SafeField(x, y) }

    private fun MutableList<MutableList<Field>>.increaseMineCountOfAdjacentFields(x: Int, y: Int) =
        getAdjacentFields(x, y).map { (x, y) -> this[y][x] }.filterIsInstance<SafeField>()
            .forEach { field -> this[field.y][field.x] = field.incremented() }

    fun withNumbers(): List<String> = numberedBoard.map { line -> line.map { it.display() }.joinToString(separator = "") }

    private fun getAdjacentFields(x: Int, y: Int): List<Pair<Int, Int>> = listOf(
        x - 1 to y - 1, x to y - 1, x + 1 to y - 1,
        x - 1 to y - 0, x to y - 0, x + 1 to y - 0,
        x - 1 to y + 1, x to y + 1, x + 1 to y + 1,
    ).filter { it.first in 0.until(width) && it.second in 0.until(height) }

    private companion object {
        private const val MINE_CHAR = '*'
        private const val EMPTY_FIELD_CHAR = ' '
    }

    private sealed class Field(val x: Int, val y: Int) {
        open fun incremented() = this
        abstract fun display(): Char
    }

    private class MineField(x: Int, y: Int) : Field(x, y) {
        override fun display() = MINE_CHAR
    }

    private class SafeField(x: Int, y: Int, private val minesCounter: Int = 0) : Field(x, y) {
        override fun incremented(): Field {
            return SafeField(x, y, minesCounter + 1)
        }

        override fun display() = if (minesCounter == 0) EMPTY_FIELD_CHAR else minesCounter.digitToChar()
    }
}
