class Pangram {

    companion object {
        private val letters = ('a'..'z').toSet()

        fun isPangram(sentence: String): Boolean = lettersOfSentence(sentence).containsAll(letters)

        private fun lettersOfSentence(sentence: String) =
                sentence.chars().mapToObj { Character.valueOf(it.toChar()).toLowerCase() }.toArray().toSet()
    }

}
