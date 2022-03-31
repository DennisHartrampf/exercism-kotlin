class Dna(sequence: String) {

    val nucleotideCounts: Map<Char, Int>

    init {
        require(sequence.matches(PATTERN))

        nucleotideCounts = ZERO_COUNTS + sequence.groupBy { it }.mapValues { it.value.size }
    }

    companion object {
        private val PATTERN = "[ACGT]*".toRegex()
        private val ZERO_COUNTS = mapOf('A' to 0, 'C' to 0, 'G' to 0, 'T' to 0)
    }

}
