package com.datojo.socialpet.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.datojo.socialpet.Model.Pet
import java.util.Date
import java.util.concurrent.TimeUnit

class PetStatus: ViewModel() {
    private var _health = mutableStateOf(1f)
    private var _hunger = mutableStateOf(.1f)
    private var _thirst = mutableStateOf(.1f)
    private var lastOnline = Date()

    val health: State<Float> = _health
    val hunger: State<Float> = _hunger
    val thirst: State<Float> = _thirst

    fun setStats(pet: Pet) {
        _health.value = pet.health
        _hunger.value = pet.thirst
        _thirst.value = pet.thirst
        lastOnline = pet.lastOnline
    }

    fun saveStats(pet: Pet): Pet {
        pet.health = _health.value
        pet.thirst = _hunger.value
        pet.thirst = _thirst.value
        pet.lastOnline = lastOnline

        return pet
    }

    fun calcStats(started: Boolean = false){
        val secondsInterval = 1f //In which Interval parts of the stat should be lost
        val magnitude = 100f //Control how much one part is
        val buffer = 3600 //When offline for extended time what time should be ignored

        if(_health.value <= 0f) {
            _health.value = 0f
            _hunger.value = 0f
            _thirst.value = 0f
        }
        else {
            val currDate = Date()
            val timeDiff = currDate.time - lastOnline.time
            var secondsTimeDiff = TimeUnit.MILLISECONDS.toSeconds(timeDiff)

            if (secondsTimeDiff >= secondsInterval) {
                if (started)
                    if (secondsTimeDiff > buffer)
                        secondsTimeDiff -= buffer
                    else
                        secondsTimeDiff = 0

                _thirst.value -= (secondsTimeDiff / secondsInterval) / magnitude
                if (_thirst.value < 0f) _thirst.value = 0f


                if (_thirst.value == 0f)
                    _hunger.value -= (secondsTimeDiff / secondsInterval) / magnitude / 2
                else
                    _hunger.value -= (secondsTimeDiff / secondsInterval) / magnitude
                if (_hunger.value < 0f) _hunger.value = 0f


                if (_hunger.value == 0f)
                    _health.value -= (secondsTimeDiff / secondsInterval) / magnitude
                else if (_health.value < 1f && _health.value > 0f)
                    _health.value += (secondsTimeDiff / secondsInterval) / magnitude / 2
                if (_health.value > 1f) _health.value = 1f

                lastOnline = currDate
            }
        }
    }

    fun feed(): Int {
        if(_health.value > 0f) {
            if (_hunger.value == 1f)
                return 1

            _hunger.value += 0.25f
            if (_hunger.value > 1f)
                _hunger.value = 1f
            return 0
        }
        return -1
    }

    fun drink(): Int {
        if (_health.value > 0f) {
            if (_thirst.value == 1f)
                return 1

            _thirst.value += 0.125f
            if (_thirst.value > 1f)
                _thirst.value = 1f
            return 0
        }
        return -1
    }
}