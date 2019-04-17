import java.util.*

fun <E> List<E>.customAppend(list: List<E>): List<E> {
    val result = this.toMutableList()
    result.addAll(list)
    return result
}

fun <E> List<E>.customFilter(function: (E) -> Boolean): List<E> {
    val result = mutableListOf<E>()
    this.forEach {
        if (function(it)) {
            result.add(it)
        }
    }
    return result
}

fun <E> List<E>.customReverse(): List<E> {
    val result = LinkedList<E>()
    this.forEach { result.addFirst(it) }
    return result
}

fun <E, T> List<E>.customFoldRight(initial: T, function: (E, T) -> T): T {
    var result = initial
    this.customReverse().forEach { result = function(it, result) }
    return result
}

fun <E, T> List<E>.customFoldLeft(initial: T, function: (T, E) -> T): T {
    var result = initial
    this.forEach { result = function(result, it) }
    return result
}

fun <E, T> List<E>.customMap(function: (E) -> T): List<T> {
    val list = mutableListOf<T>()
    this.forEach { list.add(function(it)) }
    return list
}

fun <E> List<List<E>>.customConcat(): List<E> {
    val list = mutableListOf<E>()
    this.forEach { list.addAll(it) }
    return list
}

val <E> List<E>.customSize: Int
    get() {
        var size = 0
        this.forEach { _ -> size++ }
        return size
    }