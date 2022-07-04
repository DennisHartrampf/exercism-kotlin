class ChainNotFoundException(msg: String) : RuntimeException(msg)

private var NEXT_ID = 0

class Domino(val left: Int, val right: Int) {

    private val id = NEXT_ID++

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Domino

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Domino(left=$left, right=$right, id=$id)"
    }

}

private class Node(val domino: Domino, val remainingDominoes: Map<Int, Set<Domino>>) {
    val children: List<Node> = remainingDominoes[domino.right]?.map {
        val remainingDominoesChild = remainingDominoes.toMutableMap()
        remainingDominoesChild[it.left] = remainingDominoesChild[it.left].without(it)
        remainingDominoesChild[it.right] = remainingDominoesChild[it.right].without(it)
        Node(it, remainingDominoesChild)
    } ?: emptyList()

    private fun Set<Domino>?.without(domino: Domino) = if (this == null) emptySet() else (this.toMutableSet() - domino)

    override fun toString(): String {
        return "Node(domino=$domino, children=$children, remainingDominoes=$remainingDominoes)"
    }

}

object Dominoes {

    fun formChain(inputDominoes: List<Domino>): List<Domino> {
        if (inputDominoes.isEmpty()) {
            return emptyList()
        }
        requirePreconditions(inputDominoes)

        val firstDomino = inputDominoes.first()

        val theMap: Map<Int, Set<Domino>> = createMap(inputDominoes.drop(1))
        val root = Node(firstDomino, theMap)

        return mutableListOf(firstDomino)
    }

    private fun createMap(dominoes: List<Domino>): Map<Int, Set<Domino>> {
        val map = mutableMapOf<Int, MutableSet<Domino>>()
        dominoes.forEach { map.merge(it.left, mutableSetOf(it)) { a, b -> (a + b).toMutableSet() } }
        return map
    }

    private fun requirePreconditions(inputDominoes: List<Domino>) {
        if (inputDominoes.flatMap { listOf(it.left, it.right) }.groupBy { it }.map { it.value.size }.any { it % 2 != 0 }) {
            throw ChainNotFoundException("Preconditions not met")
        }
    }

    fun formChain(vararg inputDominoes: Domino) = formChain(inputDominoes.toList())
}
