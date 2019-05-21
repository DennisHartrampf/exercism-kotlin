internal class RotationalCipher(private val rotation: Int) {

    fun encode(input: String): String = input.map { it.encodeChar() }.joinToString(separator = "")

    private fun Char.encodeChar(): Char = if (this.isLetter()) encodeLetter() else this

    private fun Char.encodeLetter(): Char {
        return if (isLowerCase()) encodeLetter(LOWER_A) else encodeLetter(UPPER_A)
    }

    private fun Char.encodeLetter(firstLetter: Int): Char {
        return ((this.toInt() + rotation - firstLetter).rem(NUMBER_OF_LETTERS) + firstLetter).toChar()
    }

    companion object {
        private const val NUMBER_OF_LETTERS = 26
        private const val LOWER_A = 'a'.toInt()
        private const val UPPER_A = 'A'.toInt()
    }

}
