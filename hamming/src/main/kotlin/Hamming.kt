class Hamming {
    companion object {
        fun compute(string1: String, string2: String): Int {
            if (string1.length != string2.length) {
                throw IllegalArgumentException("left and right strands must be of equal length.")
            }
            return string1.zip(string2).count { pair -> pair.first != pair.second }
        }
    }
}