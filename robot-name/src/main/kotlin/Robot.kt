import java.util.*

class Robot {

    var name: String = generateName()

    fun reset() {
        val oldName = name
        name = generateName()
        USED_NAMES.remove(oldName)
    }

    private fun generateName(): String {
        repeat(MAX_TRIES) {
            val randomName = "${randomLetter()}${randomLetter()}${randomDigit()}${randomDigit()}${randomDigit()}"
            if (USED_NAMES.add(randomName)) {
                return randomName
            }
        }
        throw IllegalStateException("Didn't find a random name!")
    }

    private fun randomDigit(): Int = RANDOM.nextInt(10)

    private fun randomLetter(): Char = CHARS[RANDOM.nextInt(CHARS.size)]

    companion object {
        private const val MAX_TRIES = 100_000
        private val CHARS = ('A'..'Z').toList()
        private val USED_NAMES = mutableSetOf<String>()
        private val RANDOM = Random()
    }
}
