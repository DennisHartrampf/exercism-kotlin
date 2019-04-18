class Triangle(sideA: Double, sideB: Double, sideC: Double) {

    constructor(sideA: Number, sideB: Number, sideC: Number) : this(sideA.toDouble(), sideB.toDouble(), sideC.toDouble())

    init {
        require(isTriangle(sideA, sideB, sideC))
    }

    private val numberOfUniqueSides = setOf(sideA, sideB, sideC).size
    val isEquilateral = numberOfUniqueSides == 1
    val isIsosceles = numberOfUniqueSides <= 2
    val isScalene = numberOfUniqueSides == 3

    private fun isTriangle(sideA: Double, sideB: Double, sideC: Double) =
            allSidesGreaterZero(sideA, sideB, sideC) && satisfiesTriangleInequality(sideA, sideB, sideC)

    private fun allSidesGreaterZero(sideA: Double, sideB: Double, sideC: Double) = sideA > 0 && sideB > 0 && sideC > 0

    private fun satisfiesTriangleInequality(sideA: Double, sideB: Double, sideC: Double) =
            2 * setOf(sideA, sideB, sideC).max()!! < sideA + sideB + sideC

}