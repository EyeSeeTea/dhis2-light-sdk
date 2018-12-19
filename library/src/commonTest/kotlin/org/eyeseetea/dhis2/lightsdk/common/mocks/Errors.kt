package org.eyeseetea.dhis2.lightsdk.common

fun error500Response() =
    "{\"httpStatus\": \"Internal Server Error\",\"httpStatusCode\": 500,\"status\": \"ERROR\"}"

fun error401Response() =
    "{\"httpStatus\": \"Unauthorized\",\"httpStatusCode\": 401,\"status\": \"ERROR\",\n" +
        "\"message\": \"Unauthorized\"}"

fun error404Response() = "<html>\n" +
    "    <head>\n" +
    "        <title>Apache Tomcat/7.0.69 - Error report</title>\n" +
    "        <style>\n" +
    "            <!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}-->\n" +
    "        </style>\n" +
    "    </head>\n" +
    "    <body>\n" +
    "        <h1>HTTP Status 404 - </h1>\n" +
    "        <HR size=\"1\" noshade=\"noshade\">\n" +
    "        <p>\n" +
    "            <b>type</b> Status report\n" +
    "        </p>\n" +
    "        <p>\n" +
    "            <b>message</b>\n" +
    "            <u></u>\n" +
    "        </p>\n" +
    "        <p>\n" +
    "            <b>description</b>\n" +
    "            <u>The requested resource is not available.</u>\n" +
    "        </p>\n" +
    "        <HR size=\"1\" noshade=\"noshade\">\n" +
    "        <h3>Apache Tomcat/7.0.69</h3>\n" +
    "    </body>\n" +
    "</html>"
