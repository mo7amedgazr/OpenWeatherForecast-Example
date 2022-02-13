package com.example.common.data.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val showLoading = MutableLiveData<Boolean>()
    open val showError = SingleLiveData<String?>()

    open fun updateLoader(show: Boolean) {
        showLoading.value = show
    }
}