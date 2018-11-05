package org.eyeseetea.dhis2.lightsdk

import org.eyeseetea.dhis2.lightsdk.common.models.D2ErrorBody
import java.io.IOException

sealed class D2Response<out S> {
    sealed class Error : D2Response<Nothing>() {
        data class NetworkConnection(val cause: String?, val e: IOException) : Error()
        data class HttpError(val httpStatusCode: Int, val errorBody: D2ErrorBody?) : Error()
    }

    data class Success<out S>(val value: S) : D2Response<S>()

    val isSuccess get() = this is Success<S>
    val isError get() = this is Error

    fun <S> success(s: S) = Success(s)

    inline fun <T> fold(error: (D2Response.Error) -> T, success: (S) -> T): T =
        when (this) {
            is D2Response.Error -> error(this)
            is D2Response.Success -> success(this.value)
        }

    inline fun <T> flatMap(f: (S) -> D2Response<T>): D2Response<T> =
        fold({ this as D2Response.Error }, f)

    inline fun <T> map(f: (S) -> T): D2Response<T> =
        flatMap { D2Response.Success(f(it)) }
}
