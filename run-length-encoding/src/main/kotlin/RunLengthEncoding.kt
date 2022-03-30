object RunLengthEncoding {
    private val ENCODE_REGEX = "(\\D)\\1+".toRegex()
    private val DECODE_REGEX = "(\\d+)(\\D)".toRegex()

    fun encoded(input: String): String = input.replace(ENCODE_REGEX) {
        val count = it.value.length
        val char = it.groupValues[1]
        "$count$char"
    }

    fun decode(input: String): String = input.replace(DECODE_REGEX) {
        val (count, char) = it.destructured
        char.repeat(count.toInt())
    }
}
