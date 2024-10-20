package com.example.bmi_calculator_with_viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BMIViewModel : ViewModel() {
    private val _height = MutableLiveData<Float>(0f) // Store height in cm
    val height: LiveData<Float> get() = _height

    private val _weight = MutableLiveData<Float>(0f)
    val weight: LiveData<Float> get() = _weight

    private val _bmiResult = MutableLiveData<String>("")
    val bmiResult: LiveData<String> get() = _bmiResult

    fun updateHeight(newHeight: Float) {
        _height.value = newHeight
        calculateBMI()
    }

    fun updateWeight(newWeight: Float) {
        _weight.value = newWeight
        calculateBMI()
    }

    @SuppressLint("DefaultLocale")
    private fun calculateBMI() {
        val heightInCm = _height.value ?: return
        val weightValue = _weight.value ?: return

        // Convert height from cm to meters -> Change this to inches and feet accordingly
        val heightInMeters = heightInCm / 100

        if (heightInMeters > 0) {
            val bmi = weightValue / (heightInMeters * heightInMeters)
            _bmiResult.value = String.format("%.2f", bmi)
        } else {
            _bmiResult.value = ""
        }
    }
}

