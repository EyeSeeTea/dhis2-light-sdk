package org.eyeseetea.dhis2.lightsdk.appinfo

import kotlinx.serialization.Serializable

@Serializable
data class AppInfo(
    val buildTime: String,
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
    val lastAnalyticsTableSuccess: String,
    val metadataSyncEnabled: Boolean,
    val revision: String,
    val serverDate: String,
    val systemId: String,
    val systemName: String,
    val userAgent: String,
    val version: String
)