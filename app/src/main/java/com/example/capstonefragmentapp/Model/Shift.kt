package com.example.capstonefrontend.Model

import java.time.LocalDate
import java.util.*

data class Shift (
    var id: String,
    var name: String,
    var startTime: String,
    var endTime: String,
    var day: Int,
    var month: Int,
    var year: Int,
    var employeeEmail: String?,
    var schoolName: String
        )

