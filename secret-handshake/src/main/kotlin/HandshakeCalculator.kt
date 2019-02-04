import Signal.*
import kotlin.math.pow

object HandshakeCalculator {

    private val actions = mapOf(0 to WINK, 1 to DOUBLE_BLINK, 2 to CLOSE_YOUR_EYES, 3 to JUMP)

    fun calculateHandshake(input: Int): List<Signal> {
        return actions.filterKeys { input isDigitSet it }.values.toList().let { if (input isDigitSet 4) it.reversed() else it }
    }

    private infix fun Int.isDigitSet(digit: Int): Boolean = 2.pow(digit).let { this and it == it }

    private fun Int.pow(n: Int): Int = this.toDouble().pow(n).toInt()
}
