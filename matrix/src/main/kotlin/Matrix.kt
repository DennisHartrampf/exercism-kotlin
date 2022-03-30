class Matrix(matrixAsString: String) {

    private val rows: List<List<Int>> = matrixAsString.lines().map { it.toIntList() }

    fun column(colNr: Int): List<Int> = rows.map { it[colNr - 1] }

    fun row(rowNr: Int): List<Int> = rows[rowNr - 1]

}

private fun String.toIntList() = trim().split(Regex("\\s+")).map { it.toInt() }
