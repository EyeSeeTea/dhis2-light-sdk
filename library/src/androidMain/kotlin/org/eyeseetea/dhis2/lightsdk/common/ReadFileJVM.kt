package org.eyeseetea.dhis2.lightsdk.common

import java.io.File
import java.io.InputStream

internal actual fun readFile(file: String): String {
    val absolutePath = getFixedPath(file)
    val inputStream: InputStream = File(absolutePath).inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun getFixedPath(file: String): String {
    val rootPath = "library"
    var absolutePath = File(".").absolutePath.replace(".", "")
    if (!absolutePath.contains(rootPath)) {
        absolutePath = absolutePath + rootPath + "/"
    }
    return absolutePath + file
}
