import kotlin.math.absoluteValue

class ZebraPuzzle {

    private val solution: Solution = generatePossibleSolutions().findSolution()

    fun drinksWater(): String = solution.houses.single { it.drink == Drinks.WATER }.nationality.toString().upperFirst()

    fun ownsZebra(): String = solution.houses.single { it.pet == Pets.ZEBRA }.nationality.toString().upperFirst()

    private fun String.upperFirst() = "${first().uppercase()}${drop(1).lowercase()}"

    private fun generatePossibleSolutions() = sequence {
        val nationalitiesPermutations = withFixedValue(Nationalities.NORWEGIAN, 0) // 10. The Norwegian lives in the first house.
        val drinksPermutations = withFixedValue(Drinks.MILK, 2) // 9. Milk is drunk in the middle house.
        val colorsPermutations = withFixedValue(Colors.BLUE, 1) // 15. The Norwegian lives next to the blue house.
            .filter { it.indexOf(Colors.GREEN) == it.indexOf(Colors.IVORY) + 1 } // 6. The green house is immediately to the right of the ivory house.
            .toMutableList()
        val petsPermutations = Pets.values().permutations().toMutableList()
        val brandsPermutations = Brands.values().permutations().toMutableList()

        reducePossibleSolutions(nationalitiesPermutations, colorsPermutations, petsPermutations, drinksPermutations, brandsPermutations)

        yieldPossibleSolutions(nationalitiesPermutations, drinksPermutations, colorsPermutations, petsPermutations, brandsPermutations)
    }

    private fun reducePossibleSolutions(
        nationalitiesPermutations: MutableList<List<Nationalities>>,
        colorsPermutations: MutableList<List<Colors>>,
        petsPermutations: MutableList<List<Pets>>,
        drinksPermutations: MutableList<List<Drinks>>,
        brandsPermutations: MutableList<List<Brands>>
    ) {
        // 2. The Englishman lives in the red house.
        filterSimpleRule(nationalitiesPermutations, Nationalities.ENGLISHMAN, colorsPermutations, Colors.RED)
        // 3. The Spaniard owns the dog.
        filterSimpleRule(nationalitiesPermutations, Nationalities.SPANIARD, petsPermutations, Pets.DOG)
        // 4. Coffee is drunk in the green house.
        filterSimpleRule(drinksPermutations, Drinks.COFFEE, colorsPermutations, Colors.GREEN)
        // 5. The Ukrainian drinks tea.
        filterSimpleRule(drinksPermutations, Drinks.TEA, nationalitiesPermutations, Nationalities.UKRAINIAN)
        // 7. The Old Gold smoker owns snails.
        filterSimpleRule(brandsPermutations, Brands.OLD_GOLD, petsPermutations, Pets.SNAILS)
        // 8. Kools are smoked in the yellow house.
        filterSimpleRule(brandsPermutations, Brands.KOOLS, colorsPermutations, Colors.YELLOW)
        // 13. The Lucky Strike smoker drinks orange juice.
        filterSimpleRule(brandsPermutations, Brands.LUCKY_STRIKE, drinksPermutations, Drinks.ORANGE_JUICE)
        // 14. The Japanese smokes Parliaments.
        filterSimpleRule(brandsPermutations, Brands.PARLIAMENTS, nationalitiesPermutations, Nationalities.JAPANESE)

        // 11. The man who smokes Chesterfields lives in the house next to the man with the fox.
        filterNextToRule(brandsPermutations, Brands.CHESTERFIELDS, petsPermutations, Pets.FOX)
        // 12. Kools are smoked in the house next to the house where the horse is kept.
        filterNextToRule(brandsPermutations, Brands.KOOLS, petsPermutations, Pets.HORSE)
    }

    private suspend fun SequenceScope<Solution>.yieldPossibleSolutions(
        nationalitiesPermutations: List<List<Nationalities>>,
        drinksPermutations: List<List<Drinks>>,
        colorsPermutations: List<List<Colors>>,
        petsPermutations: List<List<Pets>>,
        brandsPermutations: List<List<Brands>>
    ) {
        for (nationalities in nationalitiesPermutations) {
            for (drinks in drinksPermutations) {
                for (colors in colorsPermutations) {
                    for (pets in petsPermutations) {
                        for (brands in brandsPermutations) {
                            val solution = Solution(
                                (0..4).map {
                                    House(
                                        nationalities[it],
                                        colors[it],
                                        pets[it],
                                        drinks[it],
                                        brands[it]
                                    )
                                }
                            )
                            yield(solution)
                        }
                    }
                }
            }
        }
    }

    private fun Sequence<Solution>.findSolution() = single { solution ->
        solution.houses.run {
            single { it.nationality == Nationalities.ENGLISHMAN }.color == Colors.RED && // 2. The Englishman lives in the red house.
                    single { it.nationality == Nationalities.SPANIARD }.pet == Pets.DOG && // 3. The Spaniard owns the dog.
                    single { it.drink == Drinks.COFFEE }.color == Colors.GREEN && // 4. Coffee is drunk in the green house.
                    single { it.drink == Drinks.TEA }.nationality == Nationalities.UKRAINIAN && // 5. The Ukrainian drinks tea.
                    single { it.brand == Brands.OLD_GOLD }.pet == Pets.SNAILS && // 7. The Old Gold smoker owns snails.
                    single { it.brand == Brands.KOOLS }.color == Colors.YELLOW && // 8. Kools are smoked in the yellow house.
                    single { it.brand == Brands.LUCKY_STRIKE }.drink == Drinks.ORANGE_JUICE && // 13. The Lucky Strike smoker drinks orange juice.
                    single { it.brand == Brands.PARLIAMENTS }.nationality == Nationalities.JAPANESE && // 14. The Japanese smokes Parliaments.
                    (indexOfFirst { it.brand == Brands.CHESTERFIELDS } - indexOfFirst { it.pet == Pets.FOX }).absoluteValue == 1 && // 11. The man who smokes Chesterfields lives in the house next to the man with the fox.
                    (indexOfFirst { it.brand == Brands.KOOLS } - indexOfFirst { it.pet == Pets.HORSE }).absoluteValue == 1 // 12. Kools are smoked in the house next to the house where the horse is kept.
        }
    }

    private inline fun <reified T : Enum<T>> withFixedValue(fixed: T, index: Int): MutableList<List<T>> {
        val permutations = enumValues<T>().filter { it != fixed }.permutations()
        return permutations.map {
            val list = it.toMutableList()
            list.add(index, fixed)
            list
        }.toMutableList()
    }

    private inline fun <reified F : Enum<F>, reified S : Enum<S>> filterSimpleRule(
        permutationsFirst: MutableList<List<F>>,
        first: F,
        permutationsSecond: MutableList<List<S>>,
        second: S
    ) {
        permutationsFirst.removeIf { firsts ->
            val indexFirst = firsts.indexOf(first)
            permutationsSecond.none { it.indexOf(second) == indexFirst }
        }
        permutationsSecond.removeIf { seconds ->
            val indexSecond = seconds.indexOf(second)
            permutationsFirst.none { it.indexOf(first) == indexSecond }
        }
    }

    private inline fun <reified F : Enum<F>, reified S : Enum<S>> filterNextToRule(
        permutationsFirst: MutableList<List<F>>,
        first: F,
        permutationsSecond: MutableList<List<S>>,
        second: S
    ) {
        permutationsFirst.removeIf { firsts ->
            val indexFirst = firsts.indexOf(first)
            permutationsSecond.none { (it.indexOf(second) - indexFirst).absoluteValue == 1 }
        }
        permutationsSecond.removeIf { seconds ->
            val indexSecond = seconds.indexOf(second)
            permutationsFirst.none { (it.indexOf(first) - indexSecond).absoluteValue == 1 }
        }
    }

    data class Solution(val houses: List<House>)

    data class House(
        val nationality: Nationalities,
        val color: Colors,
        val pet: Pets,
        val drink: Drinks,
        val brand: Brands
    )

    enum class Nationalities {
        ENGLISHMAN, SPANIARD, UKRAINIAN, NORWEGIAN, JAPANESE
    }

    enum class Colors {
        RED, GREEN, IVORY, YELLOW, BLUE
    }

    enum class Pets {
        DOG, SNAILS, FOX, HORSE, ZEBRA
    }

    enum class Drinks {
        COFFEE, TEA, MILK, ORANGE_JUICE, WATER
    }

    enum class Brands {
        OLD_GOLD, KOOLS, CHESTERFIELDS, LUCKY_STRIKE, PARLIAMENTS
    }
}

private fun <V> Array<V>.permutations(): List<List<V>> = toList().permutations()

private fun <V> List<V>.permutations(): List<List<V>> {
    val retVal: MutableList<List<V>> = mutableListOf()

    fun <V> swap(list: MutableList<V>, i: Int, k: Int) {
        val t = list[i]
        list[i] = list[k]
        list[k] = t
    }

    fun generate(k: Int, list: MutableList<V>) {
        // If only 1 element, just output the array
        if (k == 1) {
            retVal.add(list.toList())
        } else {
            for (i in 0 until k) {
                generate(k - 1, list)
                if (k % 2 == 0) {
                    swap(list, i, k - 1)
                } else {
                    swap(list, 0, k - 1)
                }
            }
        }
    }

    generate(this.count(), this.toMutableList())
    return retVal
}
