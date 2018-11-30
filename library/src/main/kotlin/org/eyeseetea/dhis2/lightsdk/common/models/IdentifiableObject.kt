package org.eyeseetea.dhis2.lightsdk.common.models

import java.util.Date

interface IdentifiableObject {
    val created: Date
    val displayName: String
    val id: String
    val lastUpdated: Date
    val name: String
}