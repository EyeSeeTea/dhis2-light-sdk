package org.eyeseetea.dhis2.lightsdk

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun <T> executePlatformCall(block: suspend () -> T): dynamic {
    return GlobalScope.promise {
        block()
    }
}
