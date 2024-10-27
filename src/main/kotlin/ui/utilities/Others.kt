package ui.utilities

import java.text.SimpleDateFormat
import java.util.*

object Others {

    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm"

    fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat(DATE_FORMAT)
        val current = formatter.format(time)

        return current
    }
}