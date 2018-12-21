package org.eyeseetea.dhis2.lightsdk.common

internal actual fun readFile(file: String): String {
    val fs = js("{require(\"fs\")}")
    return fs.readFileSync(file, "utf8")
}