package models

object BaseValues {

    var isDebug: Boolean = false

    //var BASE_URL = "https://streaming-app-3nf3.onrender.com/"
    // System.getProperty("baseUrl")
    var BASE_URL = "http://localhost:3000/"
   // var BASE_URL = if(isDebug) "http://localhost:3000/"
    //    else System.getProperty("baseUrl")

    var KEY = ""
    var PhoneRegex = ""
    var PhoneSample = ""

    fun setDebug() {
        isDebug = true
    }

    fun setPorTest() {
        isDebug = false
    }
}