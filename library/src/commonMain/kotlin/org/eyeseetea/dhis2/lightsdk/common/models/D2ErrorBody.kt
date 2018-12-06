package org.eyeseetea.dhis2.lightsdk.common.models

import kotlinx.serialization.Serializable

@Serializable
data class D2ErrorBody(
    val httpStatus: String,
    val httpStatusCode: Int,
    val status: String,
    val message: String?
)