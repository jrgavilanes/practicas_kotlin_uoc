import java.time.format.DateTimeFormatter
import java.util.*

val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy")

fun uniqueID() = UUID.randomUUID().toString()
