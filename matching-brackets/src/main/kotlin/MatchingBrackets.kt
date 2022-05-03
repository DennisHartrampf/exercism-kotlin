object MatchingBrackets {

    private val OPENING_BRACKETS = setOf('(', '[', '{')
    private val CLOSING_BRACKETS = setOf(')', ']', '}')
    private val MATCHING_BRACKETS = CLOSING_BRACKETS.zip(OPENING_BRACKETS).toMap()

    fun isValid(input: String): Boolean = input.fold(mutableListOf<Char>()) { stack, char ->
        when (char) {
            in OPENING_BRACKETS -> {
                stack.add(char)
            }
            in CLOSING_BRACKETS -> {
                val openingBracket = stack.removeLastOrNull()
                if (MATCHING_BRACKETS[char] != openingBracket) {
                    return false
                }
            }
        }
        stack
    }.isEmpty()

}
