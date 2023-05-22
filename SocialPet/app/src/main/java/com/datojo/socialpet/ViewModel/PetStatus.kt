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
    private var _social = mutableStateOf(.1f)
    private var lastOnline = Date()

    val health: State<Float> = _health
    val hunger: State<Float> = _hunger
    val social: State<Float> = _social

    fun setStats(pet: Pet) {
        _health.value = pet.health
        _hunger.value = pet.hunger
        _social.value = pet.social
        lastOnline = pet.lastOnline
    }

    fun saveStats(pet: Pet): Pet {
        pet.health = _health.value
        pet.hunger = _hunger.value
        pet.social = _social.value
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
            _social.value = 0f
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

                _social.value -= (secondsTimeDiff / secondsInterval) / magnitude
                if (_social.value < 0f) _social.value = 0f


                if (_social.value == 0f)
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

    fun pet(): Int {
        if (_health.value > 0f) {
            if (_social.value == 1f)
                return 1

            _social.value += 0.125f
            if (_social.value > 1f)
                _social.value = 1f
            return 0
        }
        return -1
    }
}