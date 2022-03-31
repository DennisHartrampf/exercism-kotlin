class DiamondPrinter {
    fun printToList(char: Char): List<String> {
        return diamondOf(char).mirrored()
    }

    private fun <T> List<T>.mirrored(): List<T> {
        if (size <= 1) {
            return this
        }
        return reversed() + subList(1, size)
    }

    private tailrec fun diamondOf(
        char: Char,
        spacing: Int = 2 * (char - START_CHAR) - 1,
        padding: Int = 0,
        list: List<String> = listOf()
    ): List<String> {
        return if (char == START_CHAR) {
            val line = padding.spaces() + "A" + padding.spaces()
            list + line
        } else {
            val line = padding.spaces() + char + spacing.spaces() + char + padding.spaces()
            diamondOf(char - 1, spacing - 2, padding + 1, list + line)
        }
    }

    private fun Int.spaces() = " ".repeat(this)

    companion object {
        private const val START_CHAR = 'A'
    }
}
