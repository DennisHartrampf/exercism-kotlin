import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class BankAccount {

    private val atomicBalance = AtomicInteger()
    private val closed = AtomicBoolean()

    val balance: Int
        get() = ifNotClosed { atomicBalance.get() }

    fun adjustBalance(difference: Int) = ifNotClosed { atomicBalance.addAndGet(difference) }

    fun close() = closed.set(true)

    private fun ifNotClosed(function: () -> Int): Int {
        return if (closed.get()) {
            throw IllegalStateException("Account is closed!")
        } else {
            function.invoke()
        }
    }

}
