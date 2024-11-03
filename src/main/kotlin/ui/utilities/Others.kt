package ui.utilities

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object Others {

    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm"

    // ***** TIME *****
    fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat(DATE_FORMAT)
        val current = formatter.format(time)

        return current
    }

    fun timeStampToLocalDateTime(timeStamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
    }

    fun isTokenExpired(currentTime: LocalDateTime, tokenDate: LocalDateTime) :  Boolean {
         return when {
             currentTime.isAfter(tokenDate) -> true
             else -> false
         }
    }


    //***** PHONE *****

    fun formatNumber(number: Double): Number {
        return if(number%1.0 == 0.0) number.toInt() else number
    }
}

data class ScoreWrapper(var temp: Double = 0.0, var tempTotal: Double= 0.0, var total: Double = 0.0)