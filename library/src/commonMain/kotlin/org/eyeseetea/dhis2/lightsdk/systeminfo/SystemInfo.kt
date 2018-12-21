package org.eyeseetea.dhis2.lightsdk.systeminfo

import kotlinx.serialization.Serializable
import org.eyeseetea.dhis2.lightsdk.common.Datetime

@Serializable
data class SystemInfo(
    val buildTime: Datetime,
    val calendar: String,
    val contextPath: String,
    val databaseInfo: DatabaseInfo,
    val dateFormat: String,
    val emailConfigured: Boolean,
    val encryption: Boolean,
    val environmentVariable: String,
    val instanceBaseUrl: String,
    val intervalSinceLastAnalyticsTableSuccess: String,
    val isMetadataVersionEnabled: Boolean,
    val jasperReportsVersion: String,
    val lastAnalyticsTableRuntime: String,
    val lastAnalyticsTableSuccess: Datetime,
    val metadataSyncEnabled: Boolean,
    val revision: String,
    val serverDate: Datetime,
    val systemId: String,
    val systemName: String,
    val userAgent: String,
    val version: String
)