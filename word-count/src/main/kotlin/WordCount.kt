import java.util.regex.Pattern

object WordCount {

    private val splitPattern = Regex("[^a-zA-Z0-9']+")
    private val quotePattern = Pattern.compile("'(.*)'")

    fun phrase(input: String) = input.toWords().normalize().toMapCounting()

    private fun String.toWords() = splitPattern.split(this).asSequence()

    private fun <T> Sequence<T>.toMapCounting(): Map<T, Int> =
            this.map { it to 1 }.fold(mutableMapOf()) { map, pair -> map.merge(pair.first, 1) { t, u -> t + u }; map }

    private fun Sequence<String>.normalize() =
            this.filter { !it.isBlank() }.map(String::toLowerCase).map {
                val matcher = quotePattern.matcher(it)
                if (matcher.matches()) matcher.group(1)
                else it
            }

}


