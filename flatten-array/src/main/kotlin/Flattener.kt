object Flattener {

    fun flatten(any: Any?): List<Any> = when {
        any is Iterable<*> -> any.flatMap { flatten(it) }
        any != null -> listOf(any)
        else -> listOf()
    }

}
