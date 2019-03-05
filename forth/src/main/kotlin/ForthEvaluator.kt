import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()
    private val definitions = mutableMapOf<String, List<Operations>>()
    private var defining = false
    private var definitionName: String? = null
    private var definitionOperations = mutableListOf<Operations>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.forEach { line ->
            line.split(" ").map { tokenize(it) }.forEach {
                evaluate(it)
            }
        }
        return stack.reversed()
    }

    private fun evaluate(token: Any) {
        if (token is List<*>) {
            token.forEach { op -> evaluateToken(op!!) }
        } else {
            evaluateToken(token)
        }
    }

    private fun tokenize(word: String): Any {
        val definition = definitions[word]
        val operation = Operations.of(word)
        return when {
            definition != null -> definition
            DEFINITION_START.matches(word) -> DefinitionStart
            DEFINITION_END.matches(word) -> DefinitionEnd
            operation != null -> operation
            NUMBER.matches(word) -> word.toInt()
            else -> word
        }
    }

    private fun evaluateToken(token: Any) {
        when {
            token is DefinitionStart -> defining = true
            token is DefinitionEnd -> define()
            defining && definitionName == null -> definitionName = token as String
            defining -> definitionOperations.add(token as Operations)
            token is Int -> stack.push(token)
            token is Operations -> token.performOperation(stack)
            else -> throw IllegalArgumentException("Unknown token: $token")
        }
    }

    private fun define() {
        defining = false
        definitions[definitionName!!] = definitionOperations
        definitionOperations = mutableListOf()
        definitionName = null
    }

    companion object {
        val NUMBER = Regex("[0-9]+")
        val DEFINITION_START = Regex(":")
        val DEFINITION_END = Regex(";")

        object DefinitionStart
        object DefinitionEnd
    }

}
