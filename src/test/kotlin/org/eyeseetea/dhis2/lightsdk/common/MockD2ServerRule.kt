package org.eyeseetea.dhis2.lightsdk.common

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockD2ServerRule(private var fileReader: FileReader) : TestRule {
    private val okHttpStatusCode = 200

    private val server: MockWebServer = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
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

    fun enqueueMockResponse(code: Int = okHttpStatusCode, response: String = "{}") {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(code)
        mockResponse.setBody(response)
        server.enqueue(mockResponse)
    }

    fun enqueueFileMockResponse(code: Int = okHttpStatusCode, fileName: String) {
        val body = fileReader.getStringFromFile(fileName)
        val response = MockResponse()
        response.setResponseCode(code)
        response.setBody(body)
        server.enqueue(response)
    }

    fun takeRequest(): RecordedRequest {
        return server.takeRequest()
    }
}