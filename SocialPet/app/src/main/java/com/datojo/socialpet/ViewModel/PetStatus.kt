package com.datojo.socialpet.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.datojo.socialpet.Model.Pet
import java.util.Date

class PetStatus: ViewModel() {
    private var _health = mutableStateOf(1f)
    private var _hunger = mutableStateOf(1f)
    private var _thirst = mutableStateOf(1f)
    private var _lastOnline = Date()

    val health: State<Float> = _health
    val hunger: State<Float> = _hunger
    val thirst: State<Float> = _thirst

    fun setStats(pet: Pet) {
        _health.value = pet.health
        _hunger.value = pet.thirst
        _thirst.value = pet.thirst
        _lastOnline = pet.lastOnline
    }

    fun saveStats(pet: Pet): Pet {
        pet.health = _health.value
        pet.thirst = _hunger.value
        pet.thirst = _thirst.value
        pet.lastOnline = _lastOnline

        return pet
    }

    val buffer: Long = 0 //When offline for extended time what time should be ignored in milliseconds
    val milliSecondsInterval = 1000f //In which Interval parts of the stat should be lost in milliseconds
    val magnitude = 100f //Control how much one part is
    fun calcStats(started: Boolean = false){
        if(_health.value <= 0f) {
            _health.value = 0f
            _hunger.value = 0f
            _thirst.value = 0f
        }
        else {
            val currDate = Date()
            var timeDiff = (currDate.time - _lastOnline.time).toFloat()

            if (timeDiff >= milliSecondsInterval) {
                if (started)
                    if (timeDiff > buffer)
                        timeDiff -= buffer
                    else
                        timeDiff = 0f


                var tmp = _thirst.value
                var thirstDrain = timeDiff
                _thirst.value -= (timeDiff / milliSecondsInterval) / magnitude
                if (thirst.value <= 0f) {
                    _thirst.value = 0f
                    thirstDrain = (tmp * milliSecondsInterval * magnitude)
                }


                tmp = _hunger.value
                var hungerDrain = timeDiff
                _hunger.value -= (thirstDrain / milliSecondsInterval) / magnitude
                _hunger.value -= ((timeDiff - thirstDrain) / milliSecondsInterval) / magnitude * 2
                if (hunger.value <= 0f) {
                    _hunger.value = 0f
                    tmp -= (thirstDrain / milliSecondsInterval) / magnitude
                    hungerDrain = (tmp * milliSecondsInterval * magnitude / 2) + thirstDrain
                }


                _health.value += (hungerDrain / milliSecondsInterval) / magnitude * 2
                if (_health.value > 1f) _health.value = 1f
                _health.value -= ((timeDiff - hungerDrain) / milliSecondsInterval) / magnitude
                if (_health.value < 0f) _health.value = 0f


                _lastOnline = currDate
            }
        }
    }

    fun feed(): Int {
        if(_health.value > 0f) {
            if (_hunger.value >= 1f)
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
            if (_thirst.value >= 1f)
                return 1

            _thirst.value += 0.125f
            if (_thirst.value > 1f)
                _thirst.value = 1f
            return 0
        }
        return -1
    }
}