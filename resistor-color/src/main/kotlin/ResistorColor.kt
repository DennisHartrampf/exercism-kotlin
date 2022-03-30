object ResistorColor {

    private val codes = arrayOf(
        "black",
        "brown",
        "red",
        "orange",
        "yellow",
        "green",
        "blue",
        "violet",
        "grey",
        "white"
    ).mapIndexed { index, color -> color to index }.toMap()

    fun colorCode(input: String): Int = codes[input]!!

    fun colors(): List<String> = codes.keys.toList()

}
