import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()
    private val definitions = mutableMapOf<String, List<Any>>()
    private var defining = false
    private var definitionName: String? = null
        set(value) = if (value != null && NUMBER.matches(value)) {
            throw IllegalArgumentException("Cannot redefine numbers")
        } else {
            field = value
        }

    private var definitionOperations = mutableListOf<Any>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.forEach { line ->
            line.split(" ").forEach {
                evaluateToken(it)
            }
        }
        return stack.reversed()
    }

    private fun evaluateToken(input: Any) {
        val token = toLowerCase(input)
        val definition = definitions[token]
        val operation = Operations.of(token)
        when {
            token == DEFINITION_END -> define()
            token is String && token.endsWith(DEFINITION_END) -> {
                evaluateToken(token.substring(0, token.length - 1))
                evaluateToken(DEFINITION_END)
            }
            token == DEFINITION_START -> defining = true
            defining && definitionName == null -> definitionName = token as String
            definition != null -> definition.forEach { evaluateToken(it) }
            defining -> definitionOperations.add(token)
            token is List<*> -> token.forEach { evaluateToken(it!!) }
            operation != null -> operation.performOperation(stack)
            token is String && NUMBER.matches(token) -> stack.push(token.toInt())
            else -> throw IllegalArgumentException("No definition available for operator \"$token\"")
        }
    }

    private fun toLowerCase(input: Any) = if (input is String) {
        input.toLowerCase()
    } else {
        input
    }

    private fun define() {
        defining = false
        definitions[definitionName!!] = definitionOperations
        definitionOperations = mutableListOf()
        definitionName = null
    }

    companion object {
        val NUMBER = Regex("[0-9]+")
        const val DEFINITION_START = ":"
        const val DEFINITION_END = ";"
    }

}
