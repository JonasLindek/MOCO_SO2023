package com.datojo.socialpet.Model

import java.util.Date

class Pet(
    var name: String,
    var breed: String,
    var age: Int,
    var health: Float,
    var hunger: Float,
    var thirst: Float,
    var lastOnline: Date,
    var currency: Int
)