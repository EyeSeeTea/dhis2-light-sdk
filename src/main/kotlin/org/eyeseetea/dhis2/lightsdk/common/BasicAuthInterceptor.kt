package org.eyeseetea.dhis2.lightsdk.common

import okhttp3.Credentials.basic
import okhttp3.Interceptor
import okhttp3.Response

internal class BasicAuthInterceptor(private val credentials: D2Credentials) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val base64Credentials = basic(credentials.username, credentials.password)

        val request = chain.request().newBuilder()
                .addHeader("Authorization", base64Credentials)
                .build()

        return chain.proceed(request)
    }
}