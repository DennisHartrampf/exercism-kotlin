object Acronym {
    fun generate(phrase: String): String {
        return phrase.split(Regex("[ -]+")).map { it.first().toUpperCase() }.joinToString(separator = "")
    }
}
