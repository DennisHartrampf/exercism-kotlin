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
            word is String && DEFINITION_START.matches(word) -> defining = true
            word is String && DEFINITION_END.matches(word) -> define()
            defining && definitionName == null -> definitionName = word as String
            defining -> definitionOperations.add(word)
            definition != null -> definition.forEach { evaluateToken(it) }
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
        val DEFINITION_START = Regex(":")
        val DEFINITION_END = Regex(";")
    }

}
