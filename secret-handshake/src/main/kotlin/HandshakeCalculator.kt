import kotlin.math.pow

object HandshakeCalculator {

    fun calculateHandshake(input: Int): List<Signal> {
        val signals = Signal.values().filterIndexed { i, _ -> input isDigitSet i }
        return if (input isDigitSet 4) signals.reversed() else signals
    }

    private infix fun Int.isDigitSet(digit: Int): Boolean {
        val pow = 2.pow(digit)
        return this@isDigitSet and pow == pow
    }

    private fun Int.pow(n: Int): Int = this.toDouble().pow(n).toInt()
}
