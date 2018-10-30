package org.eyeseetea.dhis2.lightsdk.optionsets

data class OptionSet(
    val created: String,
    val displayName: String,
    val id: String,
    val lastUpdated: String,
    val name: String,
    val options: List<Option>,
    val version: Int
)

