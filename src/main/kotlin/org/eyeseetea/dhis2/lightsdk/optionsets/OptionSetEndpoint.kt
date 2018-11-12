package org.eyeseetea.dhis2.lightsdk.optionsets

import org.eyeseetea.dhis2.lightsdk.D2Endpoint
import org.eyeseetea.dhis2.lightsdk.D2Response
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponse

class OptionSetEndpoint internal constructor(private val optionSetRetrofit: OptionSetRetrofit) :
    D2Endpoint<D2CollectionResponse<OptionSet>>() {

    fun getAll(): D2Response<List<OptionSet>> {
        val queryMap = HashMap<String, String>()

        queryMap["paging"] = "false"

        queryMap["fields"] = "id,name,displayName,created,lastUpdated,access," +
            "version,options[id,name,displayName,created,lastUpdated,access," +
            "code,attributeValues[*,attribute[id,code]]]"

        val call = optionSetRetrofit.getOptionSets(queryMap)

        val d2Response = execute(call)

        return d2Response.map { it.items }
    }
}