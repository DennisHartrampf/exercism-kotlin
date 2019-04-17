object Flattener {

    fun flatten(input: Any?, flatList: List<Any> = listOf()): List<Any> {
        return when {
            input is Iterable<*> -> input.map { flatten(it, flatList) }.reduce { acc, list -> acc + list }
            input != null -> flatList + input
            else -> flatList
        }
    }

}
