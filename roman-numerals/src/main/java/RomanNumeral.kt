class RomanNumeral {
    companion object {
        private val numberMappings = listOf(
                1000 to "M",
                900 to "CM",
                500 to "D",
                400 to "CD",
                100 to "C",
                90 to "XC",
                50 to "L",
                40 to "XL",
                10 to "X",
                9 to "IX",
                5 to "V",
                4 to "IV",
                1 to "I"
        )

        fun value(input: Int): String {
            var remainder = input
            val romanNumber = StringBuilder()

            for (numberMapping in numberMappings) {
                while (remainder >= numberMapping.first) {
                    romanNumber.append(numberMapping.second)
                    remainder -= numberMapping.first
                }
            }

            return romanNumber.toString()
        }
    }
}
