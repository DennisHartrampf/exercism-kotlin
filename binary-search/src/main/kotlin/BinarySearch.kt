object BinarySearch {
    fun <T : Comparable<T>> search(list: List<T>, element: T): Int {
        var left = 0
        var right = list.size
        while (left < right) {
            val size = right - left
            val pivotIndex = left + size / 2
            val pivotElement = list[pivotIndex]
            when {
                pivotElement == element -> return pivotIndex
                element < pivotElement -> right = pivotIndex
                else -> left = pivotIndex + 1
            }
        }
        return -1
    }

}
