package org.eyeseetea.dhis2.lightsdk

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.eyeseetea.dhis2.lightsdk.common.BasicAuthInterceptor
import org.eyeseetea.dhis2.lightsdk.common.D2Credentials
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetEndpoint
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSetRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.MalformedURLException


class D2Api(val url: String, val credentials: D2Credentials) {
    private val retrofit:Retrofit

    init {
        val apiUrl = createApiUrl()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client =  OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(BasicAuthInterceptor(credentials))
                .build()


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

    private fun createApiUrl(): HttpUrl {
        var apiUrl = HttpUrl.parse(url) ?: throw MalformedURLException()

        apiUrl = apiUrl.newBuilder()
                .addPathSegment("api")
                .build()

        return HttpUrl.parse(apiUrl.toString() + "/")!!
    }

    class Builder {
        var url: String? = null
        var credentials: D2Credentials? = null


        fun url(url: String): Builder{
            this.url = url
            return this
        }

        fun credentials(username: String, password: String): Builder {
            this.credentials = D2Credentials(username,password)
            return this;
        }

        fun build(): D2Api {
            if (url == null)
                throw java.lang.IllegalArgumentException("url is required")
            if (credentials == null)
                throw IllegalArgumentException("credentials is required")

            return D2Api(this.url!!,this.credentials!!);
        }

    }
}