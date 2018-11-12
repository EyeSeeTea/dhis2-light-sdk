package org.eyeseetea.dhis2.lightsdk.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.eyeseetea.dhis2.lightsdk.common.deserializers.D2ResponseDeserializer
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponse

inline fun <reified T> Gson.fromFile(filename: String): T? {
    val jsonString = FileReader().getStringFromFile(filename)

    val listType = object : TypeToken<T>() {}.type

    return this.fromJson<T>(jsonString, listType)
}

fun GsonBuilder.createD2Gson() = this
    .registerTypeAdapter(
        D2CollectionResponse::class.java,
        D2ResponseDeserializer()
    )
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    .create()!!