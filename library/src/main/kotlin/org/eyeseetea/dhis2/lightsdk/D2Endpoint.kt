package org.eyeseetea.dhis2.lightsdk

import com.google.gson.Gson
import org.eyeseetea.dhis2.lightsdk.common.models.D2ErrorBody
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

open class D2Endpoint<T> {
    fun execute(call: Call<T>): D2Response<T> {
        var response: Response<T>? = null
        try {
            response = call.execute()
        } catch (e: IOException) {
            return D2Response.Error.NetworkConnection(e.message, e)
        }

        return if (response!!.isSuccessful) {
            D2Response.Success(response.body()!!)
        } else {
            parseHttpError(response)
        }
    }

    private fun parseHttpError(response: Response<T>): D2Response.Error {
        var d2ErrorBody: D2ErrorBody? = null

        try {
            val gson = Gson()
            val errorBody = response.errorBody()!!.string()
            d2ErrorBody = gson.fromJson(errorBody, D2ErrorBody::class.java)
        } catch (e: Exception) {
            // do nothing if errorBody body can not be parsed
        }

        return D2Response.Error.HttpError(response.code(), d2ErrorBody)
    }
}