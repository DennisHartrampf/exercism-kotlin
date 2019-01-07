import java.time.LocalDate
import java.time.LocalDateTime

class Gigasecond(birthDate: LocalDateTime) {
    val date: LocalDateTime = birthDate.plusSeconds(GIGA_SECOND)

    constructor(birthDate: LocalDate) : this(birthDate.atStartOfDay())

    companion object {
        const val GIGA_SECOND = 1e9.toLong()
    }

}