import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()
    private val definitions = mutableMapOf<String, List<Any>>()
    private var defining = false
    private var definitionName: String? = null
    private var definitionOperations = mutableListOf<Any>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.forEach { line ->
            line.split(" ").map { tokenize(it) }.forEach {
                evaluateToken(it)
            }
        }
        return stack.reversed()
    }

    private fun tokenize(word: String): Any {
        val definition = definitions[word]
        val operation = Operations.of(word)
        return when {
            definition != null -> word
            DEFINITION_START.matches(word) -> DefinitionStart
            DEFINITION_END.matches(word) -> DefinitionEnd
            operation != null -> operation
            NUMBER.matches(word) -> word.toInt()
            else -> word
        }
    }

    private fun evaluateToken(token: Any) {
        val definition = definitions[token]
        when {
            token is List<*> -> token.forEach { evaluateToken(it!!) }
            token is DefinitionStart -> defining = true
            token is DefinitionEnd -> define()
            defining && definitionName == null -> definitionName = token as String
            defining -> definitionOperations.add(token)
            token is Int -> stack.push(token)
            token is Operations -> token.performOperation(stack)
            definition != null -> definition.forEach { evaluateToken(it) }
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
