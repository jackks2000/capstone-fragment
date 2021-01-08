package com.example.capstonefragmentapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstonefrontend.Model.Shift
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.withTimeout

class ShiftRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _shifts: MutableLiveData<ArrayList<Shift>> = MutableLiveData()

    val shifts: LiveData<ArrayList<Shift>> get() = _shifts

    suspend fun getShiftsFromDate(day: Int, month: Int, year: Int){
        val shifts = arrayListOf<Shift>()
        try {
            val result = withTimeout(5_000) {
                firestore.collection("shifts")
                    .whereEqualTo("day", day)
                    .whereEqualTo("month", month)
                    .whereEqualTo("year", year)
                    .whereEqualTo("employeeEmail", "")
                    .get().addOnSuccessListener { it ->

                        it.documents.forEach {
                            shifts.add(
                                Shift(
                                    it.getString("id").toString(),
                                    it.getString("name").toString(),
                                    it.getString("startTime").toString(),
                                    it.getString("endTime").toString(),
                                    it.getLong("day")!!.toInt(),
                                    it.getLong("month")!!.toInt(),
                                    it.getLong("year")!!.toInt(),
                                    it.getString("employeeEmail").toString(),
                                    it.getString("schoolName").toString()
                                )
                            )
                        }

                        _shifts.value = shifts
                    }
            }
        } catch (e: Exception) {
            throw ShiftsResponseException(e.message.toString(), e)
        }
    }

    suspend fun getShiftsFromEmail(email: String){
        val shifts = arrayListOf<Shift>()
        try {
            val result = withTimeout(5_000) {
                firestore.collection("shifts")
                    .whereEqualTo("employeeEmail", email)
                    .get().addOnSuccessListener { it ->
                        it.documents.forEach {
                            shifts.add(
                                Shift(
                                    it.getString("id").toString(),
                                    it.getString("name").toString(),
                                    it.getString("startTime").toString(),
                                    it.getString("endTime").toString(),
                                    it.getLong("day")!!.toInt(),
                                    it.getLong("month")!!.toInt(),
                                    it.getLong("year")!!.toInt(),
                                    it.getString("employeeEmail").toString(),
                                    it.getString("schoolName").toString()
                                )
                            )
                        }
                        _shifts.value = shifts
                    }
            }
        } catch (e: Exception) {
            throw ShiftsResponseException(e.message.toString(), e)
        }
    }

    suspend fun createShift(shift: Shift){
        try {
            //firestore has support for coroutines via the extra dependency we've added :)
            withTimeout(5_000) {
                firestore.collection("shifts").document(shift.id)
                    .set(shift)

                _shifts.value?.add(shift)
            }

        }  catch (e : Exception) {
            throw ShiftsResponseException(e.message.toString(), e)
        }
    }

    suspend fun updateShift(shift: Shift){
        try {
            val map = mapOf(Pair("employeeEmail", shift.employeeEmail))
            println("MAP ${map.get("employeeEmail")}")
            //firestore has support for coroutines via the extra dependency we've added :)
            withTimeout(5_000) {
                firestore.collection("shifts").document(shift.id)
                    .update(map)
            }

        }  catch (e : Exception) {
            throw ShiftsResponseException(e.message.toString(), e)
        }
    }

    class ShiftsResponseException(message: String, cause: Throwable) :
        Throwable(message, cause)

}