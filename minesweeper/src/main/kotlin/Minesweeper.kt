data class MinesweeperBoard(val board: List<String>) {

    private val numberedBoard: List<List<Field>>
    private val width = board.firstOrNull()?.length ?: 0
    private val height = board.size

    init {
        numberedBoard = board.map { line -> line.map { char -> if (char == MINE_CHAR) MineField else SafeField() } }
        calculateMineCounts()
    }

    private fun calculateMineCounts() = numberedBoard.forEachIndexed { y, line ->
        line.forEachIndexed { x, field ->
            if (field is MineField) {
                getAdjacentFields(x, y).map { (x, y) -> numberedBoard[y][x] }.filterIsInstance<SafeField>().forEach(SafeField::increment)
            }
        }
    }

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

    private sealed class Field {
        open fun increment() {}
        abstract fun display(): Char
    }

    private object MineField : Field() {
        override fun display() = MINE_CHAR
    }

    private class SafeField : Field() {
        private var minesCounter = 0

        override fun increment() {
            minesCounter++
        }

        override fun display() = if (minesCounter == 0) EMPTY_FIELD_CHAR else minesCounter.digitToChar()
    }
}
