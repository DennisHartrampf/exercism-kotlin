class ChainNotFoundException(msg: String) : RuntimeException(msg)

data class Domino(val left: Int, val right: Int) {
    fun flipped(): Domino {
        return Domino(right, left)
    }
}

object Dominoes {

    fun formChain(inputDominoes: List<Domino>): List<Domino> {
        requireBla(inputDominoes)
        if (inputDominoes.isEmpty()) {
            return emptyList()
        }
        val remainingDominoes = inputDominoes.toMutableList()
        var lastDomino = remainingDominoes.removeFirst()
        val outputDominoes = mutableListOf(lastDomino)
        while (remainingDominoes.isNotEmpty()) {
            lastDomino = remainingDominoes.firstOrNull { lastDomino.right == it.right || lastDomino.right == it.left } ?: throw ChainNotFoundException("Blub")
            remainingDominoes.remove(lastDomino)
            outputDominoes.add(if (outputDominoes.last().right == lastDomino.left) lastDomino else lastDomino.flipped())
        }
        return outputDominoes
    }

    private fun requireBla(inputDominoes: List<Domino>) {
        if (inputDominoes.flatMap { listOf(it.left, it.right) }.groupBy { it }.map { it.value.size }.any { it % 2 != 0 }) {
            throw ChainNotFoundException("Bla")
        }
    }

    fun formChain(vararg inputDominoes: Domino) = formChain(inputDominoes.toList())
}
