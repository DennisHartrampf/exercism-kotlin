data class MinesweeperBoard(val board: List<String>) {

    private val numberedBoard: List<String>
    private val width = board.firstOrNull()?.length ?: 0
    private val height = board.size

    init {
        numberedBoard = board.toNumberedBoard()
    }

    fun withNumbers(): List<String> = numberedBoard

    private fun List<String>.toNumberedBoard() = toMutableList().apply {
        forEachIndexed { y, line ->
            line.forEachIndexed { x, currentField ->
                if (currentField == MINE_CHAR) {
                    increaseMineCountOfAdjacentFields(x, y)
                }
            }
        }
    }

    private fun MutableList<String>.increaseMineCountOfAdjacentFields(mineX: Int, mineY: Int) =
        getAdjacentFields(mineX, mineY).forEach { (x, y) -> this[y] = this[y].incremented(x) }

    private fun String.incremented(x: Int) = StringBuilder(this).apply { setCharAt(x, this[x].incremented()) }.toString()

    private fun Char.incremented() = when (this) {
        MINE_CHAR -> MINE_CHAR
        EMPTY_FIELD_CHAR -> '1'
        else -> this + 1
    }

    private fun getAdjacentFields(x: Int, y: Int): List<Pair<Int, Int>> = listOf(
        x - 1 to y - 1, x to y - 1, x + 1 to y - 1,
        x - 1 to y - 0, x to y - 0, x + 1 to y - 0,
        x - 1 to y + 1, x to y + 1, x + 1 to y + 1,
    ).filter { it.first in 0.until(width) && it.second in 0.until(height) }

    private companion object {
        private const val MINE_CHAR = '*'
        private const val EMPTY_FIELD_CHAR = ' '
    }

}
