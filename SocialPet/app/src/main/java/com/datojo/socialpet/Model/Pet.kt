package com.datojo.socialpet.Model

import java.util.Date

class Pet(
    val name: String,
    val breed: String,
    val age: Int,
    var health: Float,
    var hunger: Float,
    var social: Float,
    var lastOnline: Date
)