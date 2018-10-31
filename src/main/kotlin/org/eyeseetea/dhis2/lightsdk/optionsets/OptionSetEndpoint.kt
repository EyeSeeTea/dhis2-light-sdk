package org.eyeseetea.dhis2.lightsdk.optionsets

class OptionSetEndpoint internal constructor(val optionSetRetrofit: OptionSetRetrofit) {
    fun getAll(): List<OptionSet> {
        try {
            val queryMap = HashMap<String, String>()

            queryMap["paging"] = "false"

            queryMap["fields"] = "id,name,displayName,created,lastUpdated,access," +
                    "version,options[id,name,displayName,created,lastUpdated,access," +
                    "code,attributeValues[*,attribute[id,code]]]"

            val call = optionSetRetrofit.getOptionSets(queryMap)

            val optionSetMap = call.execute().body()!!

            return optionSetMap["optionSets"]!!
        } catch (e: Exception) {
            throw e
        }
    }
}