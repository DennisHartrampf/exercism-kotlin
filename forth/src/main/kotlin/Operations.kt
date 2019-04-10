import java.util.*

enum class Operations(val token: kotlin.String, private val spokenName: kotlin.String, private val requiredOperands: Int, private val operation: (LinkedList<Int>) -> Unit) {
    PLUS("+", "Addition", 2, { it performBinaryOperation { o1, o2 -> o1 + o2 } }),
    MINUS("-", "Subtraction", 2, { it performBinaryOperation { o1, o2 -> o1 - o2 } }),
    MULTIPLICATION("*", "Multiplication", 2, { it performBinaryOperation { o1, o2 -> o1 * o2 } }),
    DIVISION("/", "Division", 2, {
        it performBinaryOperation { o1, o2 ->
            if (o2 == 0) {
                throw IllegalArgumentException("Division by 0 is not allowed")
            } else {
                o1 / o2
            }
        }
    }),

    DUPLICATE("dup", "Duplicating", 1, { it.push(it.peek()) }),
    DROP("drop", "Dropping", 1, { it.pop() }),
    SWAP("swap", "Swapping", 2, {
        val first = it.pop()
        val second = it.pop()
        it.push(first)
        it.push(second)
    }),
    OVER("over", "Overing", 2, {
        it.push(it[1])
    }),
    ;

    fun performOperation(stack: LinkedList<Int>) {
        if (stack.size < requiredOperands) {
            throw IllegalArgumentException("$spokenName requires that the stack contain at least " +
                    "$requiredOperands ${if (requiredOperands == 1) "value" else "values"}")
        }
        operation.invoke(stack)
    }

    companion object {
        private val map = values().map { Pair(it.token.toLowerCase(), it) }.toMap()

        fun of(word: Any): Operations? {
            return map[word]
        }

        private infix fun LinkedList<Int>.performBinaryOperation(binaryOperation: (Int, Int) -> Int) {
            val secondOperand = this.pop()
            val firstOperand = this.pop()
            this.push(binaryOperation.invoke(firstOperand, secondOperand))
        }
    }
}