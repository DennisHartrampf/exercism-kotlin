object Flattener {

    fun flatten(input: Any?): List<Any> {
        return flatten(mutableListOf(), input)
    }

    private fun flatten(flatList: MutableList<Any>, any: Any?): List<Any> {
        when {
            any is Iterable<*> -> any.forEach { flatten(flatList, it) }
            any != null -> flatList.add(any)
        }
        return flatList
    }

}
