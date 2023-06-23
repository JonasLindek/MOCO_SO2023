package com.datojo.socialpet.Model

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

data class Items(
    var food: Int,
    var water: Int,
    var currency: Int
) {
    fun saveToInternalStorage(filename: String, context: Context) {
        try {
            val fOut = context.openFileOutput(filename, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fOut)

            writer.write(food.toString() + "\n")
            writer.write(water.toString() + "\n")
            writer.write(currency.toString() + "\n")

            writer.close()
            fOut.close()
        } catch(e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFromInternalStorage(filename: String, context: Context): Items {
        try {
            val fIn = context.openFileInput(filename)
            val streamReader = InputStreamReader(fIn)
            val bufferedReader = BufferedReader(streamReader)

            food = bufferedReader.readLine().toInt()
            water = bufferedReader.readLine().toInt()
            currency = bufferedReader.readLine().toInt()

            bufferedReader.close()
            streamReader.close()
            fIn.close()
        } catch(e: IOException) {
            e.printStackTrace()
        }

        return this
    }
}