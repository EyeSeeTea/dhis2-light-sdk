package org.eyeseetea.dhis2.lightsdk.common

internal actual fun readFile(file: String): String {
    val fs = js("{require(\"fs\")}")
    return fs.readFileSync(getFixedPath(file), "utf8")
}

fun getFixedPath(file: String): String {
    val path = "src\\commonTest\\resources\\"
    return "$path$file"
}