internal class Matrix(matrix: MyMatrix) {

    val saddlePoints = matrix.coordinates()
            .filter { (y, x) -> matrix.hasSaddlePointAt(y, x) }.toCoordinates()

    private fun MyMatrix.coordinates(): List<Pair<Int, Int>> {
        return mapIndexed { y, row -> row.indices.map { x -> y to x } }.flatten()
    }

    private fun MyMatrix.hasSaddlePointAt(row: Int, column: Int): Boolean {
        val value = this[row][column]
        val columns = this.map { it[column] }
        return this[row].all { value >= it } && columns.all { value <= it }
    }

    private fun List<Pair<Int, Int>>.toCoordinates(): Set<MatrixCoordinate> {
        return map { (y, x) -> MatrixCoordinate(y, x) }.toSet()
    }

}

private typealias MyMatrix = List<List<Int>>
