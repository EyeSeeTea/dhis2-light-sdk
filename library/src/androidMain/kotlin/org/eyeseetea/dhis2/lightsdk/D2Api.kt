package org.eyeseetea.dhis2.lightsdk

import kotlinx.coroutines.runBlocking

actual fun <T> executePlatformCall(block: suspend () -> T): T {
    return runBlocking { block() }
}