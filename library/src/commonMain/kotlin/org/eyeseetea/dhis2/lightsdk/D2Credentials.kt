package org.eyeseetea.dhis2.lightsdk

data class D2Credentials(val username: String, val password: String) {
    companion object {
        fun empty(): D2Credentials = D2Credentials("", "")
    }
}