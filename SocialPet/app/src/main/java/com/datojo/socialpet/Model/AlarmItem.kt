package com.datojo.socialpet.Model

import java.io.Serializable
import java.util.Date

data class AlarmItem(
    val id: Int,
    val message: String,
    val date: Date
    ) : Serializable