internal class Squares(upperBound: Int) {
    private val squareOfSum by lazy { (1..upperBound).sum().square() }
    private val sumOfSquares by lazy { (1..upperBound).sumBy { it.square() } }
    private val difference by lazy { squareOfSum - sumOfSquares }

    fun squareOfSum(): Int = squareOfSum

    fun sumOfSquares(): Int = sumOfSquares

    fun difference(): Int = difference

    private fun Int.square(): Int = this * this
}
