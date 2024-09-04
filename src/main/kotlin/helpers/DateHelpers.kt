package helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class DateHelpers {


}

fun CoroutineScope.startCountDown(totalMinutes: Int, onTick: (String) -> Unit) {
    launch {
        var totalSeconds = totalMinutes * 60
        while (totalSeconds >= 0) {
            val currentMinutes = totalSeconds/ 60
            val currentSeconds = totalSeconds%60
            onTick(String.format("%02d:%02d", currentMinutes, currentSeconds))
            if(totalSeconds == 0) {
                break
            }
            delay(1000)
            totalSeconds --
        }
    }
}