class BankAccount {

    private var closed = false

    var balance: Int = 0
        @Synchronized get() = ifNotClosed { field }

    @Synchronized
    fun adjustBalance(difference: Int) = ifNotClosed { balance += difference }

    @Synchronized
    fun close() {
        closed = true
    }

    @Synchronized
    private fun <T> ifNotClosed(function: () -> T): T {
        return if (closed) {
            throw IllegalStateException("Account is closed!")
        } else {
            function.invoke()
        }
    }

}
