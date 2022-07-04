class ChainNotFoundException(msg: String) : RuntimeException(msg)

private var NEXT_ID = 0

class Domino(val left: Int, val right: Int, private val id: Int = NEXT_ID++) {

    override fun equals(other: Any?): Boolean = other is Domino && other.id == id

    override fun hashCode(): Int = id

    override fun toString(): String = "Domino(left=$left, right=$right, id=$id)"

    fun flipped(): Domino = Domino(right, left, id)
}

private class Node(private val domino: Domino, remainingDominoes: Map<Int, Set<Domino>>, private val parent: Node?) {
    val solutions = mutableListOf<List<Domino>>()

    init {
        if (remainingDominoes.values.all { it.isEmpty() }) {
            registerSolution(listOf())
        }
    }

    val children: List<Node> = remainingDominoes[domino.right]?.map {
        val remainingDominoesChild = remainingDominoes.toMutableMap()
        remainingDominoesChild[it.left] = remainingDominoesChild[it.left].without(it)
        remainingDominoesChild[it.right] = remainingDominoesChild[it.right].without(it)
        Node(it, remainingDominoesChild, this)
    } ?: emptyList()

    private fun Set<Domino>?.without(domino: Domino) = if (this == null) emptySet() else (this.toMutableSet() - domino)

    private fun registerSolution(partialSolution: List<Domino>) {
        val solution = listOf(this.domino) + partialSolution
        if (parent == null) {
            solutions.add(solution)
        } else {
            parent.registerSolution(solution)
        }
    }

    override fun toString(): String = "Node(domino=$domino, children=$children)"

}

object Dominoes {

    fun formChain(inputDominoes: List<Domino>): List<Domino> {
        if (inputDominoes.isEmpty()) {
            return emptyList()
        }
        requirePreconditions(inputDominoes)

        val remainingDominoes = findRemainingDominoes(inputDominoes.drop(1))
        val root = Node(inputDominoes.first(), remainingDominoes, null)

        return root.solutions.firstOrNull() ?: throw ChainNotFoundException("No chain found")
    }

    private fun findRemainingDominoes(dominoes: List<Domino>): Map<Int, Set<Domino>> =
        (dominoes + dominoes.map { it.flipped() }).groupBy { it.left }.mapValues { it.value.toSet() }

    private fun requirePreconditions(inputDominoes: List<Domino>) {
        if (inputDominoes.flatMap { listOf(it.left, it.right) }.groupBy { it }.map { it.value.size }.any { it % 2 != 0 }) {
            throw ChainNotFoundException("Preconditions not met")
        }
    }

    fun formChain(vararg inputDominoes: Domino) = formChain(inputDominoes.toList())
}
