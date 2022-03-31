class Dna(sequence: String) {

    val nucleotideCounts: Map<Char, Int>

    init {
        require(sequence.matches(PATTERN))

        nucleotideCounts = ZERO_COUNTS + sequence.groupingBy { it }.eachCount()
    }

    companion object {
        private const val NUCLEOTIDES = "ACGT"
        private val PATTERN = "[$NUCLEOTIDES]*".toRegex()
        private val ZERO_COUNTS = NUCLEOTIDES.associateWith { 0 }
    }

}
