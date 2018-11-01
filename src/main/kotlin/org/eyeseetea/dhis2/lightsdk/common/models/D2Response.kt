package org.eyeseetea.dhis2.lightsdk.common.models

class D2Response<T>(
    val pager: Pager?,
    var items: List<T>
)