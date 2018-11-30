package org.eyeseetea.dhis2.lightsdk.common.models

data class D2CollectionResponse<T>(
    val pager: Pager?,
    var items: List<T>
)
