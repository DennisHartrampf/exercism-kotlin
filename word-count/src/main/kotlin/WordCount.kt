import java.util.regex.Pattern

object WordCount {

    private val splitPattern = Regex("[^a-zA-Z0-9']+")
    private val quotePattern = Pattern.compile("'(.*)'")

    fun phrase(input: String) = input.toWords().normalize().toMapCounting()

    private fun String.toWords() = split(splitPattern).asSequence()

    private fun <T> Sequence<T>.toMapCounting(): Map<T, Int> = groupBy { it }.mapValues { it.value.size }

    private fun Sequence<String>.normalize() =
            filter { !it.isBlank() }.map(String::toLowerCase).map {
                val matcher = quotePattern.matcher(it)
                if (matcher.matches()) matcher.group(1)
                else it
            }

}


