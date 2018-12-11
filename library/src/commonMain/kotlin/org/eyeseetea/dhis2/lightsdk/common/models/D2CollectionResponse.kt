package org.eyeseetea.dhis2.lightsdk.common.models

// import kotlinx.serialization.Decoder
// import kotlinx.serialization.KSerializer
// import kotlinx.serialization.Serializable
// import kotlinx.serialization.SerializationException
// import kotlinx.serialization.Serializer
// import kotlinx.serialization.json.JSON
// import kotlinx.serialization.json.JsonObject
// import kotlinx.serialization.json.JsonTreeMapper
// import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSet

// Generic serialization in javascript is broken forJs
// For the moment we are going to create a D2CollectionResponse for every
// endpoint as OptionSetCollection, when this bug is solved we should use this class
// to avoid create similar classes
// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#about-generic-serializers
// https://github.com/Kotlin/kotlinx.serialization/issues/244
// @Serializable(with = D2CollectionResponseCustomSerializer::class)
// class D2CollectionResponse<T>(
//    val pager: Pager?,
//    var items: List<T>
// )
//
// /**
// * The collection response in Dhis2 Api always contains pager and items,
// * where items key name is variable according to type (events, optionSets ...).
// * for this reason with kotlinx serialization is necessary to create a specific deserializer
// */
// @Serializer(forClass = D2CollectionResponse::class)
// class D2CollectionResponseCustomSerializer<T>(private val dataSerializer: KSerializer<T>) :
//    KSerializer<D2CollectionResponse<T>> {
//
//    override fun deserialize(input: Decoder): D2CollectionResponse<T> {
//        val jsonReader = input as? JSON.JsonInput
//            ?: throw SerializationException("This class can be loaded only by JSON")
//        val tree = jsonReader.readAsTree() as? JsonObject
//            ?: throw SerializationException("Expected JSON object")
//
//        var items = mutableListOf<T>()
//        var pager: Pager? = null
//
//        for (entry in tree.content) {
//            if (entry.key == "pager") {
//                pager = JsonTreeMapper().readTree(entry.value, Pager.serializer())
//            } else {
//                for (jsonElement in entry.value.jsonArray) {
//                    items.add(JsonTreeMapper().readTree(jsonElement, dataSerializer))
//                }
//            }
//        }
//
//        return D2CollectionResponse(pager, items)
//    }
// }