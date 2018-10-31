package org.eyeseetea.dhis2.lightsdk.common.reader

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class FileReader {
    fun getStringFromFile(filename: String): String {
        val inputStream = FileInputStream(getFile(javaClass, filename))

        val isr = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(isr)
        val sb = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            sb.append(line)
            line = bufferedReader.readLine()
        }

        return sb.toString()
    }

    private fun getFile(clazz: Class<*>, filename: String): File {
        val classLoader = clazz.classLoader
        val resource = classLoader.getResource(filename)
        return File(resource!!.path)
    }
}