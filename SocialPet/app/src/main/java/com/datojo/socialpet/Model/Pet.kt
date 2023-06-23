package com.datojo.socialpet.Model

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Date

class Pet(
    var name: String,
    var breed: String,
    var age: Int,
    var health: Float,
    var hunger: Float,
    var thirst: Float,
    var lastOnline: Date
) {
    fun saveToInternalStorage(filename: String, context: Context) {
        try {
            val fOut = context.openFileOutput(filename, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fOut)

            writer.write(name + "\n")
            writer.write(breed + "\n")
            writer.write(age.toString() + "\n")
            writer.write(health.toString() + "\n")
            writer.write(hunger.toString() + "\n")
            writer.write(thirst.toString() + "\n")
            writer.write(lastOnline.time.toString() + "\n")

            writer.close()
            fOut.close()
        } catch(e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFromInternalStorage(filename: String, context: Context): Pet{
        try {
            val fIn = context.openFileInput(filename)
            val streamReader = InputStreamReader(fIn)
            val bufferedReader = BufferedReader(streamReader)

            name = bufferedReader.readLine()
            breed = bufferedReader.readLine()
            age = bufferedReader.readLine().toInt()
            health = bufferedReader.readLine().toFloat()
            hunger = bufferedReader.readLine().toFloat()
            thirst = bufferedReader.readLine().toFloat()
            lastOnline = Date(bufferedReader.readLine().toLong())

            bufferedReader.close()
            streamReader.close()
            fIn.close()
        } catch(e: IOException) {
            e.printStackTrace()
        }

        return this
    }
}