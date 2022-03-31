class Dna(sequence: String) {

    init {
        require(sequence.matches(PATTERN))
    }

    val nucleotideCounts: Map<Char, Int> = ZERO_COUNTS + sequence.groupingBy { it }.eachCount()

    companion object {
        private const val NUCLEOTIDES = "ACGT"
        private val PATTERN = "[$NUCLEOTIDES]*".toRegex()
        private val ZERO_COUNTS = NUCLEOTIDES.associateWith { 0 }
    }

}
