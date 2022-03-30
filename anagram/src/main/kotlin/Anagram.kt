class Anagram(source: String) {

    private val lowercaseSource = source.lowercase()

    fun match(anagrams: Collection<String>): Set<String> {
        val sortedLowercaseSource = lowercaseSource.sortedLowercaseChars()
        return anagrams.filter { it.sortedLowercaseChars() == sortedLowercaseSource && it.lowercase() != lowercaseSource }.toSet()
    }

    private fun String.sortedLowercaseChars() = lowercase().toCharArray().sorted()
}
