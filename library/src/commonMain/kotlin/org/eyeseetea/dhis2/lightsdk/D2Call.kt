package org.eyeseetea.dhis2.lightsdk

import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.executePlatformCall

class D2Call <T> (private val call: suspend () -> D2Response<T> ){
    suspend fun suspendExecute() : D2Response<T>{
        return call()
    }

    fun execute() : D2Response<T>{
        return executePlatformCall{
            call()
        }
    }
}