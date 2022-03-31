class DiamondPrinter {
    fun printToList(char: Char): List<String> = char.toDiamond()

    private fun Char.toDiamond(): List<String> = toHalfDiamond(2 * (this - START_CHAR) + 1, 0, listOf()).mirrored()

    private tailrec fun Char.toHalfDiamond(size: Int, offset: Int, diamond: List<String>): List<String> {
        val newDiamond = diamond + this.toDiamondLine(size, offset)
        return if (this == START_CHAR) {
            newDiamond
        } else {
            (this - 1).toHalfDiamond(size, offset + 1, newDiamond)
        }
    }

    private fun Char.toDiamondLine(size: Int, offset: Int): String =
        CharArray(size) { ' ' }.apply {
            this[offset] = this@toDiamondLine
            this[size - offset - 1] = this@toDiamondLine
        }.joinToString(separator = "")

    private fun <T> List<T>.mirrored(): List<T> = if (size <= 1) {
        this
    } else {
        reversed() + subList(1, size)
    }

    companion object {
        private const val START_CHAR = 'A'
    }
}
