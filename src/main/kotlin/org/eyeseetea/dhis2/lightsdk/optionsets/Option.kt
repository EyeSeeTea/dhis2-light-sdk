package org.eyeseetea.dhis2.lightsdk.optionsets

data class Option(
        val attributeValues: List<Any>,
        val code: String,
        val created: String,
        val displayName: String,
        val id: String,
        val lastUpdated: String,
        val name: String
)