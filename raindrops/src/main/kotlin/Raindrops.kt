object Raindrops {

    private val RULES = mapOf(3 to "Pling", 5 to "Plang", 7 to "Plong")

    fun convert(n: Int): String = RULES.map { if (n % it.key == 0) it.value else "" }.joinToString(separator = "").ifEmpty { n.toString() }
}
