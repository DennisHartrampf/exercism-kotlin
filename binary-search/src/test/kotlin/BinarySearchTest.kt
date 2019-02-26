import org.junit.Test
import kotlin.test.assertEquals

class BinarySearchTest {

    @Test
    fun findsTheItemInASingleElementList() {
        assertEquals(0, BinarySearch.search(listOf(6), 6))
    }

    @Test
    fun findsTheItemInATwoElementList() {
        assertEquals(0, BinarySearch.search(listOf(0, 1), 0))
        assertEquals(1, BinarySearch.search(listOf(0, 1), 1))
    }

    @Test
    fun findsTheItemInAThreeElementList() {
        assertEquals(0, BinarySearch.search(listOf(0, 1, 2), 0))
        assertEquals(1, BinarySearch.search(listOf(0, 1, 2), 1))
        assertEquals(2, BinarySearch.search(listOf(0, 1, 2), 2))
    }

    @Test
    fun findsTheItemInTheMiddleOfAList() {
        assertEquals(3, BinarySearch.search(listOf(1, 3, 4, 6, 8, 9, 11), 6))
    }

    @Test
    fun findsTheItemAtTheBeginningOfAList() {
        assertEquals(0, BinarySearch.search(listOf(1, 3, 4, 6, 8, 9, 11), 1))
    }

    @Test
    fun findsTheItemAtTheEndOfAList() {
        assertEquals(6, BinarySearch.search(listOf(1, 3, 4, 6, 8, 9, 11), 11))
    }

    @Test
    fun findsValueInAListOfOddLength() {
        val list = (0..12).toList()
        (0..12).forEach {
            assertEquals(it, BinarySearch.search(list, it))
        }
    }

    @Test
    fun findsValueInAListOfEvenLength() {
        val list = (0..13).toList()
        (0..13).forEach {
            assertEquals(it, BinarySearch.search(list, it))
        }
    }

    @Test
    fun identifiesThatAValueIsNotInTheList() {
        assertEquals(-1, BinarySearch.search(listOf(1, 3, 4, 6, 8, 9, 11), 7))
    }

    @Test
    fun identifiesThatAValueSmallerThanTheSmallestListElementIsNotInTheList() {
        assertEquals(-1, BinarySearch.search(listOf(1, 3, 4, 6, 8, 9, 11), 0))
    }

    @Test
    fun identifiesThatAValueLargerThanTheLargestListElementIsNotInTheList() {
        assertEquals(-1, BinarySearch.search(listOf(1, 3, 4, 6, 8, 9, 11), 13))
    }

    @Test
    fun identifiesThatNothingCanBeFoundInAnEmptyList() {
        assertEquals(-1, BinarySearch.search(emptyList(), 1))
    }

}
