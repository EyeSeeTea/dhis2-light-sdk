package org.eyeseetea.dhis2.lightsdk.common.models

data class Pager(
    val nextPage: String,
    val page: Int,
    val pageCount: Int,
    val pageSize: Int,
    val total: Int
)