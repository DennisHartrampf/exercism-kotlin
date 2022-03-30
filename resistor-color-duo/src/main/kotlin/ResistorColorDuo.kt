object ResistorColorDuo {

    fun value(vararg colors: Color): Int = colors.take(2).map { it.ordinal }.joinToString("").toInt()

}
