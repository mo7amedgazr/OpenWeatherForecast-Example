package com.example.common.data.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    companion object {
        private const val PREF = "MyAppPref"
        private const val PREF_UNIT = "weather_unit"
        private const val PREF_LAST_CURRENT_FETCH_TIME = "last_fetch_time"
        private const val PREF_LAST_KNOWN_LATITUDE = "last_lat"
        private const val PREF_LAST_KNOWN_LONGITUDE = "last_lon"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)


    fun saveUnit(unit: String) {
        val editor = sharedPref.edit()
        editor.putString(PREF_UNIT, unit)
        editor.apply()
    }

    fun getUnit(): String {
        return sharedPref.getString(PREF_UNIT, Units.FAHRENHEIT.unit).toString()
    }

    fun saveLastFetch(time: Long) {
        val editor = sharedPref.edit()
        editor.putLong(PREF_LAST_CURRENT_FETCH_TIME, time)
        editor.apply()
    }

    fun getLastFetch(): Long {
        return sharedPref.getLong(PREF_LAST_CURRENT_FETCH_TIME, 0)

    }

    fun saveLastKnowLocation(latitude: Double, longitude: Double) {
        saveLastLatitude(latitude)
        saveLastLongitude(longitude)
    }

    private fun saveLastLatitude(latitude: Double) {
        val editor = sharedPref.edit()
        editor.putLong(PREF_LAST_KNOWN_LATITUDE, java.lang.Double.doubleToRawLongBits(latitude))
        editor.apply()
    }

    fun getLastLatitude(): Double {
        return java.lang.Double.longBitsToDouble(
            sharedPref.getLong(
                PREF_LAST_KNOWN_LATITUDE,
                java.lang.Double.doubleToRawLongBits(0.0)
            )
        )
    }

    private fun saveLastLongitude(latitude: Double) {
        val editor = sharedPref.edit()
        editor.putLong(PREF_LAST_KNOWN_LONGITUDE, java.lang.Double.doubleToRawLongBits(latitude))
        editor.apply()
    }

    fun getLastLongitude(): Double {
        return java.lang.Double.longBitsToDouble(
            sharedPref.getLong(
                PREF_LAST_KNOWN_LONGITUDE,
                java.lang.Double.doubleToRawLongBits(0.0)
            )
        )
    }

    fun isLastKnownLocationAvailable(): Boolean = sharedPref.contains(PREF_LAST_KNOWN_LONGITUDE)

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

}