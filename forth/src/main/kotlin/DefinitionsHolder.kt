internal class DefinitionsHolder {
    private val definitions = mutableMapOf<String, List<Any>>()
    var defining = false
        private set
    var definitionName: String? = null
        set(value) = if (value != null && ForthEvaluator.NUMBER.matches(value)) {
            throw IllegalArgumentException("Cannot redefine numbers")
        } else {
            field = value
        }

    private var definitionOperations = mutableListOf<Any>()

    fun definitionFor(input: Any) = definitions[input]?.toList()

    fun startDefining() {
        defining = true
    }

    fun endDefining() {
        defining = false
        definitions[definitionName!!] = definitionOperations
        definitionOperations = mutableListOf()
        definitionName = null
    }

    fun addOperation(token: Any) {
        definitionOperations.add(token)
    }

    fun needsName() = defining && definitionName == null
}