import kotlin.math.pow

object HandshakeCalculator {

    fun calculateHandshake(input: Int): List<Signal> {
        return Signal.values().filterIndexed { i, _ -> input isDigitSet i }.let { if (input isDigitSet 4) it.reversed() else it }
    }

    private infix fun Int.isDigitSet(digit: Int): Boolean = 2.pow(digit).let { this and it == it }

    private fun Int.pow(n: Int): Int = this.toDouble().pow(n).toInt()
}
