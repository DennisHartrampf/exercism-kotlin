import java.util.*

class ForthEvaluator {
    private val stack = LinkedList<Int>()
    private val definitions = mutableMapOf<String, List<Any>>()
    private var defining = false
    private var definitionName: String? = null
    private var definitionOperations = mutableListOf<Any>()

    fun evaluateProgram(instructions: List<String>): List<Int> {
        instructions.forEach { line ->
            line.split(" ").forEach {
                evaluateToken(it)
            }
        }
        return stack.reversed()
    }

    private fun evaluateToken(word: Any) {
        val definition = definitions[word]
        val operation = if (word is String) Operations.of(word) else null
        when {
            word == DEFINITION_END -> define()
            word is String && word.endsWith(DEFINITION_END) -> {
                evaluateToken(word.substring(0, word.length - 1))
                evaluateToken(";")
            }
            word == DEFINITION_START -> defining = true
            defining && definitionName == null -> definitionName = word as String
            definition != null -> definition.forEach { evaluateToken(it) }
            defining -> definitionOperations.add(word)
            word is List<*> -> word.forEach { evaluateToken(it!!) }
            operation != null -> operation.performOperation(stack)
            word is String && NUMBER.matches(word) -> stack.push(word.toInt())
            else -> throw IllegalArgumentException("Unknown word: $word")
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
        const val DEFINITION_START = ":"
        const val DEFINITION_END = ";"
    }

}
