import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.flatMap { it.split(" ") }.map { tokenize(it) }.forEach(this@ForthEvaluator::evaluate)
        return stack.reversed()
    }

    private fun tokenize(word: String): Any {
        val binaryOperation = BinaryOperations.of(word)
        val stackOperation = StackOperations.of(word)
        return when {
            binaryOperation != null -> binaryOperation
            stackOperation != null -> stackOperation
            NUMBER.matches(word) -> word.toInt()
            else -> throw IllegalArgumentException("Unknown word: " + word)
        }
    }

    private fun evaluate(token: Any) {
        when (token) {
            is Int -> stack.push(token)
            is BinaryOperations -> performBinaryOperation(token)
            is StackOperations -> performStackOperation(token)
        }
    }

    private fun performBinaryOperation(token: BinaryOperations) {
        if (stack.size < 2) {
            throw IllegalArgumentException("${token.spokenName} requires that the stack contain at least 2 values")
        }
        val secondOperand = stack.pop()
        val firstOperand = stack.pop()
        stack.push(token.operation.invoke(firstOperand, secondOperand))
    }

    private fun performStackOperation(token: StackOperations) {
        if (stack.size < token.requiredOperands) {
            throw IllegalArgumentException("${token.spokenName} requires that the stack contain at least " +
                    "${token.requiredOperands} ${if (token.requiredOperands == 1) "value" else "values"}")
        }
        token.operation.invoke(stack)
    }

    companion object {
        val NUMBER = Regex("[0-9]+")
    }
}

enum class BinaryOperations(val token: kotlin.String, val spokenName: kotlin.String, val operation: (Int, Int) -> Int) {
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

        fun of(word: String): BinaryOperations? {
            return map[word]
        }
    }
}

enum class StackOperations(val token: kotlin.String, val spokenName: kotlin.String, val requiredOperands: Int, val operation: (LinkedList<Int>) -> Unit) {
    DUPLICATE("dup", "Duplicating", 1, { it.push(it.peek()) }),
    DROP("drop", "Dropping", 1, { it.pop() }),
    SWAP("swap", "Swapping", 2, {
        val first = it.pop();
        val second = it.pop()
        it.push(first)
        it.push(second)
    }),
    OVER("over", "Overing", 2, {
        it.push(it[1])
    }),
    ;

    companion object {
        private val map = values().map { Pair(it.token.toLowerCase(), it) }.toMap()

        fun of(word: String): StackOperations? {
            return map[word.toLowerCase()]
        }
    }
}