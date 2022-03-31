class IsbnVerifier {

    fun isValid(number: String): Boolean {
        if (!PATTERN.matches(number)) {
            return false
        }
        val numbers = number.filter { it.isDigit() || it == 'X' }.map { if (it == 'X') 10 else it.digitToInt() }
        return numbers.reversed().reduceIndexed { index, checksum, it -> checksum + it * (index + 1) } % 11 == 0
    }

    companion object {
        private val PATTERN = "\\d-?\\d{3}-?\\d{5}-?[0-9X]".toRegex()
    }
}
