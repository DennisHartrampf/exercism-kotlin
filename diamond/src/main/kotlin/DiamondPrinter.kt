class DiamondPrinter {
    fun printToList(char: Char): List<String> {
        require(char in START_CHAR..MAX_CHAR)

        val charRange = START_CHAR..char
        val size = charRange.count()
        val quarterDiamond = charRange.map { it.toQuarterDiamondLine(size) }
        return quarterDiamond.mirroredVertically().mirroredHorizontally()
    }

    private fun Char.toQuarterDiamondLine(size: Int) = toString().padStart(size - (this - START_CHAR)).padEnd(size)

    private fun List<String>.mirroredVertically() = map { it.toList().mirroredHorizontally().joinToString("") }

    private fun <T> List<T>.mirroredHorizontally() = dropLast(1) + reversed()

    companion object {
        private const val START_CHAR = 'A'
        private const val MAX_CHAR = 'Z'
    }
}
