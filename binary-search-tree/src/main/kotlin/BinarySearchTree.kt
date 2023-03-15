class BinarySearchTree<T : Comparable<T>> {

    data class Node<T>(val data: T, var left: Node<T>? = null, var right: Node<T>? = null)

    var root: Node<T>? = null

    fun insert(value: T) {
        if (root == null) {
            root = Node(value)
        } else {
            root!!.insert(value)
        }
    }

    private fun Node<T>.insert(value: T) {
        if (value <= data) {
            insertLeft(value)
        } else {
            insertRight(value)
        }
    }

    private fun Node<T>.insertLeft(value: T) {
        if (left == null) {
            left = Node(value)
        } else {
            left!!.insert(value)
        }
    }

    private fun Node<T>.insertRight(value: T) {
        if (right == null) {
            right = Node(value)
        } else {
            right!!.insert(value)
        }
    }

    fun asSortedList(): List<T> = if (root == null) emptyList() else root!!.dfsInfix().map { it.data }

    private fun Node<T>?.dfsInfix(): List<Node<T>> = if (this == null) emptyList() else left.dfsInfix() + this + right.dfsInfix()

    fun asLevelOrderList(): List<T> = if (root == null) emptyList() else listOf(root!!).asLevelOrderList().map { it.data }

    private fun List<Node<T>>.asLevelOrderList(): List<Node<T>> = if (isEmpty()) emptyList() else this + buildNextLevel().asLevelOrderList()

    private fun List<Node<T>>.buildNextLevel() =
        map { listOfNotNull(it.left, it.right) }.fold<List<Node<T>>, List<Node<T>>>(listOf()) { acc, nodes -> acc + nodes }

}
