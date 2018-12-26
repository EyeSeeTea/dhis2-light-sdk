package org.eyeseetea.dhis2.lightsdk.useraccount

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val username: String
)