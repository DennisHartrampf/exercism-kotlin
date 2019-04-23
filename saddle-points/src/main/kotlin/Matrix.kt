internal class Matrix(matrix: MyMatrix) {

    val saddlePoints = matrix.coordinates().filter { (y, x) -> matrix.hasSaddlePointAt(y, x) }.toCoordinates()

    private fun MyMatrix.coordinates() = mapIndexed { y, row -> row.indices.map { x -> y to x } }.flatten()

    private fun MyMatrix.hasSaddlePointAt(row: Int, column: Int): Boolean {
        val value = this[row][column]
        return this[row].all { value >= it } && this.map { it[column] }.all { value <= it }
    }

    private fun List<Pair<Int, Int>>.toCoordinates() = map { (y, x) -> MatrixCoordinate(y, x) }.toSet()

}

private typealias MyMatrix = List<List<Int>>