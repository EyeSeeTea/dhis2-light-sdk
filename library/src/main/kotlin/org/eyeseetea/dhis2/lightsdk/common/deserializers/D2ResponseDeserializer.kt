package org.eyeseetea.dhis2.lightsdk.common.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponse
import org.eyeseetea.dhis2.lightsdk.common.models.IdentifiableObject
import org.eyeseetea.dhis2.lightsdk.common.models.Pager
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * The collection response in Dhis2 Api always contains pager and items,
 * where items key name is variable according to type (events, optionSets ...).
 * for this reason with gson is necessary to create a specific deserializer
 */
class D2ResponseDeserializer : JsonDeserializer<D2CollectionResponse<IdentifiableObject>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): D2CollectionResponse<IdentifiableObject> {

        val genericType = (typeOfT as ParameterizedType).actualTypeArguments[0]
        val jsonObject = json?.asJsonObject

        var pager: Pager? = null
        val items = mutableListOf<IdentifiableObject>()

        for (entry in jsonObject?.entrySet()!!) {
            context?.let {
                if (entry.key == "pager") {
                    pager = it.deserialize(entry.value, Pager::class.java)!!
                } else {
                    val jsonArray = entry.value.asJsonArray

                    for (jsonElement in jsonArray) {
                        items.add(it.deserialize(jsonElement, genericType)!!)
                    }
                }
            }
        }

        return D2CollectionResponse(pager, items)
    }
}