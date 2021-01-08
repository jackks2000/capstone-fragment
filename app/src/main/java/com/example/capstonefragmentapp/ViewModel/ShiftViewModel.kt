package com.example.capstonefragmentapp.ViewModel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstonefragmentapp.Repository.ShiftRepository
import com.example.capstonefrontend.Model.Shift
import kotlinx.coroutines.launch

class ShiftViewModel(application: Application): AndroidViewModel(application) {
    private val shiftRepository = ShiftRepository()
    val shifts = shiftRepository.shifts
    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String> get() = _errorText

    fun getShiftsFromDate(day: Int, month: Int, year: Int){
        viewModelScope.launch {
            try {
                shiftRepository.getShiftsFromDate(day, month, year)
            } catch (error: ShiftRepository.ShiftsResponseException) {
                _errorText.value = error.message
                Log.e("Shift error", error.cause.toString())
            }
        }
    }

    fun getShiftsFromEmail(email: String){
        viewModelScope.launch {
            try {
                shiftRepository.getShiftsFromEmail(email)
            } catch (error: ShiftRepository.ShiftsResponseException) {
                _errorText.value = error.message
                Log.e("Shift error", error.cause.toString())
            }
        }
    }

    fun createShift(shift: Shift) {
        // persist data to firestore
        viewModelScope.launch {
            try {
                shiftRepository.createShift(shift)
            } catch (ex: ShiftRepository.ShiftsResponseException) {
                val errorMsg = "Something went wrong while saving shift"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }

    fun updateShift(shift: Shift) {
        // persist data to firestore
        viewModelScope.launch {
            try {
                println("VM EMAIL: " + shift.employeeEmail)
                shiftRepository.updateShift(shift)
            } catch (ex: ShiftRepository.ShiftsResponseException) {
                val errorMsg = "Something went wrong while saving shift"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }


}