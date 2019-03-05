import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.flatMap { it.split(" ") }.map { tokenize(it) }.forEach(this@ForthEvaluator::evaluate)
        return stack.reversed()
    }

    private fun tokenize(word: String): Any {
        val token = Token.of(word)
        return when {
            token != null -> token
            NUMBER.matches(word) -> word.toInt()
            else -> throw IllegalArgumentException("Unknown word: " + word)
        }
    }

    private fun evaluate(token: Any) {
        when (token) {
            is Int -> stack.push(token)
            is Token -> performBinaryOperation(token)
        }
    }

    private fun performBinaryOperation(token: Token) {
        if (stack.size < 2) {
            throw IllegalArgumentException("${token.spokenName} requires that the stack contain at least 2 values")
        }
        val secondOperand = stack.pop()
        val firstOperand = stack.pop()
        stack.push(token.operation.invoke(firstOperand, secondOperand))
    }

    companion object {
        val NUMBER = Regex("[0-9]+")
    }
}

enum class Token(val token: kotlin.String, val spokenName: kotlin.String, val operation: (Int, Int) -> Int) {
    PLUS("+", "Addition", { o1, o2 -> o1 + o2 }),
    MINUS("-", "Subtraction", { o1, o2 -> o1 - o2 }),
    MULTIPLICATION("*", "Multiplication", { o1, o2 -> o1 * o2 }),
    DIVISION("/", "Division", { o1, o2 ->
        if (o2 == 0) {
            throw IllegalArgumentException("Division by 0 is not allowed")
        } else {
            o1 / o2
        }
    }),
    ;

    companion object {
        private val map = values().map { Pair(it.token, it) }.toMap()

        fun of(word: String): Token? {
            return map[word]
        }
    }
}