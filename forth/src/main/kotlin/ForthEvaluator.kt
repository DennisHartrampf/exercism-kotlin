import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.flatMap { it.split(" ") }.map { tokenize(it) }.forEach { evaluate(it) }
        return stack.reversed()
    }

    private fun tokenize(word: String): Any {
        val operation = Operations.of(word)
        return when {
            operation != null -> operation
            NUMBER.matches(word) -> word.toInt()
            else -> throw IllegalArgumentException("Unknown word: " + word)
        }
    }

    private fun evaluate(token: Any) {
        when (token) {
            is Int -> stack.push(token)
            is Operations -> token.performOperation(stack)
        }
    }

    companion object {
        val NUMBER = Regex("[0-9]+")
    }
}
