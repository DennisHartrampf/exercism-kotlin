class Triangle(sideA: Double, sideB: Double, sideC: Double) {

    constructor(sideA: Number, sideB: Number, sideC: Number) : this(sideA.toDouble(), sideB.toDouble(), sideC.toDouble())

    init {
        if (!isTriangle(sideA, sideB, sideC)) {
            throw IllegalArgumentException()
        }
    }

    val isEquilateral = sideA == sideB && sideA == sideC
    val isIsosceles = sideA == sideB || sideA == sideC || sideB == sideC
    val isScalene = !isIsosceles

    private fun isTriangle(sideA: Double, sideB: Double, sideC: Double) =
            allSidesGreaterZero(sideA, sideB, sideC) &&
                    allSidesSatisfyTriangleInequality(sideA, sideB, sideC)

    private fun allSidesGreaterZero(sideA: Double, sideB: Double, sideC: Double) = sideA > 0 && sideB > 0 && sideC > 0

    private fun allSidesSatisfyTriangleInequality(sideA: Double, sideB: Double, sideC: Double) =
            satisfiesTriangleInequality(sideA, sideB, sideC) &&
                    satisfiesTriangleInequality(sideA, sideC, sideB) &&
                    satisfiesTriangleInequality(sideB, sideC, sideA)

    private fun satisfiesTriangleInequality(side1: Double, side2: Double, side3: Double) = side1 + side2 >= side3

}