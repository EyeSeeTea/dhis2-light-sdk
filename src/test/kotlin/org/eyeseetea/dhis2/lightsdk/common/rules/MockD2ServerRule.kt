package org.eyeseetea.dhis2.lightsdk.common.rules

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.eyeseetea.dhis2.lightsdk.common.reader.FileReader
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockD2ServerRule (var fileReader: FileReader): TestRule {

    private val server: MockWebServer

    init {
        this.server = MockWebServer()
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                before()

                base.evaluate()

                after()
            }
        }
    }

    private fun before() {
        server.start()
    }

    private fun after() {
        server.shutdown()
    }

    val baseEndpoint: String
        get() = server.url("/").toString()

    fun shutdown() {
        server.shutdown()
    }

    fun enqueueMockResponse(code: Int = OK_CODE, response: String = "{}") {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(code)
        mockResponse.setBody(response)
        server.enqueue(mockResponse)
    }

    fun enqueueFileMockResponse(code: Int = OK_CODE, fileName: String) {
        val body = fileReader.getStringFromFile(fileName)
        val response = MockResponse()
        response.setResponseCode(code)
        response.setBody(body)
        server.enqueue(response)
    }


    fun takeRequest(): RecordedRequest {
        return server.takeRequest()
    }

    companion object {
        private val OK_CODE = 200
    }
}