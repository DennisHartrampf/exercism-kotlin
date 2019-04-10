import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()
    private val definitionsHolder = DefinitionsHolder()

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
        val definition = definitionsHolder.definitionFor(token)
        val operation = Operations.of(token)
        when {
            token == DEFINITION_END -> definitionsHolder.endDefining()
            token is String && token.endsWith(DEFINITION_END) -> {
                evaluateToken(token.substring(0, token.length - 1))
                evaluateToken(DEFINITION_END)
            }
            token == DEFINITION_START -> definitionsHolder.startDefining()
            definitionsHolder.needsName() -> definitionsHolder.definitionName = token as String
            definition != null -> definition.forEach { evaluateToken(it) }
            definitionsHolder.defining -> definitionsHolder.addOperation(token)
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

    companion object {
        val NUMBER = Regex("\\d+")
        const val DEFINITION_START = ":"
        const val DEFINITION_END = ";"
    }

}
