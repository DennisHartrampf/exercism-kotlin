class DiamondPrinter {
    fun printToList(char: Char): List<String> {
        val size = 2 * (char - START_CHAR) + 1
        val lowerHalfOfDiamond = char.downTo(START_CHAR).mapIndexed { offset, it -> it.toDiamondLine(size, offset) }
        return lowerHalfOfDiamond.mirrored()
    }

    private fun Char.toDiamondLine(size: Int, offset: Int): String =
        CharArray(size) { ' ' }.also {
            it[offset] = this
            it[size - offset - 1] = this
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
