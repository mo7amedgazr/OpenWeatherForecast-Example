package com.example.setting

import androidx.lifecycle.ViewModel
import com.example.common.data.preferences.SharedPrefs
import com.example.common.data.preferences.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val sharedPrefs: SharedPrefs) : ViewModel() {

    fun getSelectedUnit(): Units {
        val unit = sharedPrefs.getUnit()
        return if (Units.CELSIUS.unit == unit) Units.CELSIUS else Units.FAHRENHEIT
    }

    fun updateUnit(unit: String) {
        sharedPrefs.saveUnit(unit)
    }

}