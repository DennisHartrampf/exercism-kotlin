class Triangle private constructor(sides: List<Double>) {

    constructor(sideA: Number, sideB: Number, sideC: Number) : this(sideA.toDouble(), sideB.toDouble(), sideC.toDouble())
    constructor(sideA: Double, sideB: Double, sideC: Double) : this(listOf(sideA, sideB, sideC))

    val isEquilateral: Boolean
    val isIsosceles: Boolean
    val isScalene: Boolean

    init {
        require(isTriangle(sides))
        val numberOfUniqueSides = sides.toSet().size
        isEquilateral = numberOfUniqueSides == 1
        isIsosceles = numberOfUniqueSides <= 2
        isScalene = numberOfUniqueSides == 3
    }

    private fun isTriangle(sides: List<Double>) = sides.all { it > 0 } && satisfiesTriangleInequality(sides)

    private fun satisfiesTriangleInequality(sides: List<Double>) = 2 * sides.max()!! < sides.sum()

}