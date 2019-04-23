internal class Matrix(matrix: MyMatrix) {

    val saddlePoints = matrix.coordinates().filter { (y, x) -> matrix.hasSaddlePointAt(y, x) }.toCoordinates()

    private fun MyMatrix.coordinates() = mapIndexed { y, row -> row.indices.map { x -> y to x } }.flatten()

    private fun MyMatrix.hasSaddlePointAt(row: Int, column: Int): Boolean {
        val value = this[row][column]
        return value.isHighestInRow(this[row]) && value.isSmallestInColumn(this.getColumn(column))
    }

    private fun List<Pair<Int, Int>>.toCoordinates() = map { (y, x) -> MatrixCoordinate(y, x) }.toSet()

    private fun Int.isSmallestInColumn(allValuesInColumn: List<Int>) = allValuesInColumn.all { this <= it }

    private fun Int.isHighestInRow(allValuesInRow: List<Int>) = allValuesInRow.all { this >= it }

    private fun MyMatrix.getColumn(column: Int) = map { it[column] }

}

private typealias MyMatrix = List<List<Int>>