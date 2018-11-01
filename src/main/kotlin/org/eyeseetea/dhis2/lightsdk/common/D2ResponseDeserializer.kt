package org.eyeseetea.dhis2.lightsdk.common

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.eyeseetea.dhis2.lightsdk.common.models.D2Response
import org.eyeseetea.dhis2.lightsdk.common.models.IdentifiableObject
import org.eyeseetea.dhis2.lightsdk.common.models.Pager
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * The collection response in Dhis2 Api always is pager and items,
 * where items key name is variable according to type (events, optionSets ...).
 * for this reason with gson is necessary to create a specific deserializer
 */
class D2ResponseDeserializer : JsonDeserializer<D2Response<IdentifiableObject>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): D2Response<IdentifiableObject> {

        val genericType = (typeOfT as ParameterizedType).actualTypeArguments[0]
        val jsonObject = json?.asJsonObject

        var pager: Pager? = null
        var items = mutableListOf<IdentifiableObject>()

        for (entry in jsonObject?.entrySet()!!) {
            if (entry.key == "pager") {
                pager = context?.deserialize(entry.value, Pager::class.java)!!
            } else {
                val jsonArray = entry.value.asJsonArray

                for (jsonElement in jsonArray) {
                    items.add(context?.deserialize(jsonElement, genericType)!!)
                }
            }
        }

        return D2Response(pager, items)
    }
}