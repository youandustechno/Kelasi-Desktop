package helpers

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class StorageHelper {

    private var connection: Connection? = null

    companion object {
        val AUTH_CODE = "authCode"
        val TOKEN_CODE = "tokenCode"
    }

    init {
        val writableDirectory = getWritableDirectory()
        val dbPath = "$writableDirectory/preferences.db"

        val directory = java.io.File(writableDirectory)
        if(!directory.exists()) {
            directory.mkdirs()
        }
        initializeDatabase(dbPath)
        createPreferencesTable()
    }

    fun initializeDatabase(dbPath: String) {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:$dbPath")
        }
        catch (e: SQLException) {

        }
    }

    private fun createPreferencesTable() {
        connection?.createStatement().use { statement ->
            statement?.execute(
                "CREATE TABLE IF NOT EXISTS preferences (key TEXT PRIMARY KEY, value TEXT)"
            )
        }
    }

    fun saveInStorage(key: String, value: String) {
        val query = "REPLACE INTO preferences (key, value) VALUES(?, ?)"
        connection?.prepareStatement(query).use { statement ->
            statement?.setString(1, key)
            statement?.setString(2, value)
            val affectedRoes = statement?.executeUpdate()
            println("$affectedRoes row(s) affected.")
        }
    }

    fun retrieveFromStorage(key: String): String? {
        val query = "SELECT value FROM preferences WHERE key = ?"
        connection?.prepareStatement(query).use { statement ->
            statement?.setString(1, key)
            val resultSet : ResultSet? = statement?.executeQuery()

            return resultSet?.getString("value")
        }
    }

    private fun getWritableDirectory() : String {
        val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

        return when {
            osName.contains("win") -> System.getenv("APPDATA") + "\\Mateya"
            osName.contains("mac") -> System.getProperty("user.home") + "/Library/Application Support/Mateya"
            osName.contains("nux") -> System.getProperty("user.home") + "/.config/Mateya"
            else -> System.getProperty("user.dir") // Fallback to current directory
        }
    }

}