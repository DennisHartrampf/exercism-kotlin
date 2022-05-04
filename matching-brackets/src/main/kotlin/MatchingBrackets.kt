object MatchingBrackets {

    private val OPENING_BRACKETS = setOf('(', '[', '{')
    private val CLOSING_BRACKETS = setOf(')', ']', '}')
    private val MATCHING_BRACKETS = CLOSING_BRACKETS.zip(OPENING_BRACKETS).toMap()

    fun isValid(input: String): Boolean = mutableListOf<Char>().let { brackets -> input.all { brackets.isValid(it) } && brackets.isEmpty() }

    private fun MutableList<Char>.isValid(char: Char): Boolean = when (char) {
        in OPENING_BRACKETS -> {
            openBracket(char)
        }
        in CLOSING_BRACKETS -> {
            closeBracket(char)
        }
        else -> true
    }

    private fun MutableList<Char>.openBracket(openingBracket: Char): Boolean {
        add(openingBracket)
        return true
    }

    private fun MutableList<Char>.closeBracket(closingBracket: Char): Boolean =
        removeLastOrNull().let { openingBracket -> MATCHING_BRACKETS[closingBracket] == openingBracket }
}
