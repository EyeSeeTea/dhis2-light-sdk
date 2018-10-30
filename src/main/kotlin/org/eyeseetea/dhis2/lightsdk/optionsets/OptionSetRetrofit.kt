package org.eyeseetea.dhis2.lightsdk.optionsets

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface OptionSetRetrofit {
    @GET("optionSets")
    fun getOptionSets(@QueryMap queryMap: Map<String, String>): Call<Map<String, List<OptionSet>>>
}