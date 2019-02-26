object BinarySearch {
    fun <T : Comparable<T>> search(list: List<T>, element: T): Int {
        return search(list, element, 0, list.size)
    }

    private fun <T : Comparable<T>> search(list: List<T>, element: T, start: Int, end: Int): Int {
        val listSize = end - start
        if (listSize == 0) {
            return -1
        }
        val pivotIndex = start + listSize / 2
        val pivotElement = list[pivotIndex]
        return when {
            pivotElement == element -> pivotIndex
            element < pivotElement -> search(list, element, start, pivotIndex)
            else -> search(list, element, pivotIndex + 1, end)
        }
    }
}
