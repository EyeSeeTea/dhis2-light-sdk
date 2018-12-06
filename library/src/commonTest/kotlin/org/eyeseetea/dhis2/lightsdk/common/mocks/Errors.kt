package org.eyeseetea.dhis2.lightsdk.common

fun error500Response() = "{\"httpStatus\": \"Internal Server Error\",\"httpStatusCode\": 500,\"status\": \"ERROR\"}"

fun error401Response() = "{\"httpStatus\": \"Unauthorized\",\"httpStatusCode\": 401,\"status\": \"ERROR\",\n" +
        "\"message\": \"Unauthorized\"}"
