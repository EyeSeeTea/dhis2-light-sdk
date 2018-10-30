package org.eyeseetea.dhis2.lightsdk.optionsets

import org.eyeseetea.dhis2.lightsdk.Dhis2Api
import org.eyeseetea.dhis2.lightsdk.common.Dhis2ApiConfig
import org.eyeseetea.dhis2.lightsdk.common.Dhis2Credentials
import org.junit.Test

class OptionSetEndpointShould{
    @Test
    fun return_optionSets() {
        val config = Dhis2ApiConfig("https://data.psi-mis.org",
                Dhis2Credentials("KEdemo1","Kenyademo1"))

        val dhis2Api = Dhis2Api(config)

        val optionSets = dhis2Api.optionSets().getOptionSets()
    }
}