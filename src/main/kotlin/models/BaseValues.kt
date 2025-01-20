package models

object BaseValues {

    var isDebug: Boolean = false

    // System.getProperty("baseUrl")
    var BASE_URL = "http://localhost:3000/"
    // var BASE_URL = "https://streaming-app-3nf3.onrender.com/"
    // var BASE_URL = if(isDebug) "http://localhost:3000/"
    //    else System.getProperty("baseUrl")

    var KEY = ""
    var PhoneRegex = ""
    var PhoneSample = ""
    var LEVELS: List<String> = emptyList()

    fun setDebug() {
        isDebug = true
    }

    fun setPorTest() {
        isDebug = false
    }

    fun getStudentsLevels() : List<String> {
        return  LEVELS.filter {! it.equals("admin", true) && !it.equals("prof", true) }
    }
}