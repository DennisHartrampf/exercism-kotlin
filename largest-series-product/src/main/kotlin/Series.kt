internal class Series(input: String) {
    private val input: List<Int> = input.map { it.toString().toInt() }

    fun getLargestProduct(windowSize: Int): Int {
        return when {
            windowSize == 0 -> 1
            windowSize > input.size -> throw IllegalArgumentException()
            else -> input.windowed(windowSize).maxBy { it.reduce(Int::times) }!!.reduce(Int::times)
        }
    }

}