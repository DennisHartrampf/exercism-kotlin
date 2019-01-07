import java.time.LocalDate
import java.time.LocalDateTime

class Gigasecond(date: LocalDateTime) {
    val date: LocalDateTime

    init {
        this.date = date.plusSeconds(GIGASECOND)
    }

    constructor(date: LocalDate) : this(date.atStartOfDay())

    companion object {
        const val GIGASECOND = 1e9.toLong()
    }

}