class Deque<T> {

    private var first: Node<T>? = null
    private var last: Node<T>? = null

    fun push(value: T) {
        val node = Node(value)
        if (first == null) {
            first = node
            last = node
        } else {
            node.previous = last
            last!!.next = node
            last = node
        }
    }

    fun unshift(value: T) {
        val node = Node(value)
        if (first == null) {
            first = node
            last = node
        } else {
            node.next = first
            first!!.previous = node
            first = node
        }
    }

    fun pop(): T {
        val node = last
        last = node!!.previous
        node.previous = null
        if (last == null) {
            first == null
        } else {
            last?.next = null
        }
        return node.value
    }

    fun shift(): T {
        val node = first
        first = node!!.next
        node.next = null
        if (first == null) {
            last = null
        } else {
            first?.previous = null
        }
        return node.value
    }

    private class Node<T>(internal val value: T) {
        internal var next: Node<T>? = null
        internal var previous: Node<T>? = null
    }
}