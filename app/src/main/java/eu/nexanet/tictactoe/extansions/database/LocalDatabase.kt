package eu.nexanet.tictactoe.extansions.database

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class LocalDatabase(
    context: Context,
    database: Database
) {

    private var reader: SharedPreferences =
        context.getSharedPreferences(database.value, MODE_PRIVATE)

    private var writer: SharedPreferences.Editor =
        context.getSharedPreferences(database.value, MODE_PRIVATE).edit()

    fun saveString(key: String, value: String) {
        writer.putString(key, value)
        writer.apply()
    }

    fun getString(key: String): String? {
        return reader.getString(key, null);
    }

    fun saveBoolean(key: String, value: Boolean) {
        writer.putBoolean(key, value)
        writer.apply()
    }

    fun getBoolean(key: String): Boolean {
        return reader.getBoolean(key, false)
    }
}