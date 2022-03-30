object Bob {
    fun hey(input: String): String = when {
        input.isSilence() -> "Fine. Be that way!"
        input.isQuestion() && input.isYelled() -> "Calm down, I know what I'm doing!"
        input.isQuestion() -> "Sure."
        input.isYelled() -> "Whoa, chill out!"
        else -> "Whatever."
    }

    private fun String.isSilence() = isBlank()

    private fun String.isQuestion() = trim().last() == '?'

    private fun String.isYelled() = all { !it.isLetter() || it.isUpperCase() } && any { it.isLetter() }
}
