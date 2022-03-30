object Isogram {

    fun isIsogram(input: String): Boolean = with(input.letters()) { length == lowercase().toSet().size }

    private fun String.letters() = filter { it.isLetter() }
}
