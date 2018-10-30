package org.eyeseetea.dhis2.lightsdk

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.eyeseetea.dhis2.lightsdk.common.BasicAuthInterceptor
import org.eyeseetea.dhis2.lightsdk.common.Dhis2ApiConfig
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetEndpoint
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Dhis2Api(val dhis2ApiConfig: Dhis2ApiConfig) {
    val retrofit:Retrofit

    init {
        val apiUrl = getApiUrl()

        val client = createOkHttpClient()

        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(apiUrl)
                .build()
    }

    fun optionSets():OptionSetEndpoint{
        val optionSetRetrofit = retrofit.create(OptionSetRetrofit::class.java)
        return OptionSetEndpoint(optionSetRetrofit)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(BasicAuthInterceptor(dhis2ApiConfig.credentials))
                .build()

        return client
    }

    private fun getApiUrl(): HttpUrl? {
        var url = HttpUrl.parse(dhis2ApiConfig.url)

        /*        if (url == null) {
                    throw ApiException.unexpectedError(
                            configuration.getServerUrl(), MalformedURLException())
                }*/

        if (url != null) {
            url = url.newBuilder()
                    .addPathSegment("api")
                    .build()
        }

        val apiUrl = HttpUrl.parse(url.toString() + "/") // TODO EW!!!
        return apiUrl
    }
}