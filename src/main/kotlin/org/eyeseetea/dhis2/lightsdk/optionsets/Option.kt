package org.eyeseetea.dhis2.lightsdk.optionsets

import org.eyeseetea.dhis2.lightsdk.common.models.IdentifiableObject
import java.util.Date

data class Option(
    val attributeValues: List<Any>,
    val code: String,
    override val created: Date,
    override val displayName: String,
    override val id: String,
    override val lastUpdated: Date,
    override val name: String
) : IdentifiableObject