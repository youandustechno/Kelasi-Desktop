package helpers

import com.optimaize.langdetect.LanguageDetector
import com.optimaize.langdetect.LanguageDetectorBuilder
import com.optimaize.langdetect.ngram.NgramExtractors
import com.optimaize.langdetect.profiles.LanguageProfile
import com.optimaize.langdetect.profiles.LanguageProfileReader
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.util.*

fun readTextFromUrl(url: String) : String {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).execute().use { response ->
        if(!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body?.toString() ?:""
    }
}

fun isFrench(text: String): Boolean {
    // Load language profiles (for all languages supported by the library)
    val languageProfiles: List<LanguageProfile> = LanguageProfileReader().readAllBuiltIn()

    // Build language detector with loaded profiles
    val languageDetector: LanguageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
        .withProfiles(languageProfiles)
        .build()

    // Detect language with a probability threshold
    val lang = languageDetector.detect(text)
    return lang.isPresent && lang.get().language == "fr"
}

fun readAloud(text: String) {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    if(osName.contains("mac")) {
        Runtime.getRuntime().exec("say $text")

    } else if(osName.contains("windows")) {
        Runtime.getRuntime().exec("powershell -Command \"Add-Type -AssemblyName System.Speech; " +
                "[System.Speech.Synthesis.SpeechSynthesizer]::new().Speak('$text');\"")

    } else {
        Runtime.getRuntime().exec("espeak \"$text\"")

    }
}

fun readAloudFrench(text: String) {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    if(osName.contains("mac")) {
        Runtime.getRuntime().exec("say -v Thomas \"$text\"")

    } else if(osName.contains("windows")) {
        Runtime.getRuntime().exec("powershell -Command \"Add-Type -AssemblyName System.Speech; " +
                "[System.Speech.Synthesis.SpeechSynthesizer]::new().Speak('$text');\"")

    } else {
        Runtime.getRuntime().exec("espeak -v fr \"$text\"")

    }
}

fun stopReading() {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    if(osName.contains("mac")) {
        Runtime.getRuntime().exec("killall say")

    } else if(osName.contains("windows")) {
        Runtime.getRuntime().exec("taskkill /IM powershell.exe /F")

    } else {
        Runtime.getRuntime().exec("pkill espeak")
    }
}

