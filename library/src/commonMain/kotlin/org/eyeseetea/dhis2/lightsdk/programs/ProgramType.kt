package org.eyeseetea.dhis2.lightsdk.programs

enum class ProgramType constructor(val value: String) {
    WITH_REGISTRATION("with_registration"),
    WITHOUT_REGISTRATION("without_registration")
}