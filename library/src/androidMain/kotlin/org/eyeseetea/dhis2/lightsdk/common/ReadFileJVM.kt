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
    var path = "/src/commonTest/resources/"
    val rootProject = "/library"

    val rootPath = System.getProperty("user.dir")
    if (!rootPath.contains(rootProject)) {
        path = rootProject + path
    }
    return "$rootPath$path$file"
}