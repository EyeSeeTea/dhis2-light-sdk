package org.eyeseetea.dhis2.lightsdk.common

import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey

object LogFeature : HttpClientFeature<Unit, LogFeature> {
    override val key: AttributeKey<LogFeature> = AttributeKey("LogFeature")

    override fun prepare(block: Unit.() -> Unit): LogFeature = this

    override fun install(feature: LogFeature, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.State) {
            logDebug(context.url.build().toString())
        }

/*        scope.responsePipeline.intercept(HttpResponsePipeline.Receive) {
            val response = subject.response as HttpResponse
            //logDebug(response.readText())
            logDebug(response.call.request.headers.toString())

            proceedWith(subject)
        }*/
    }
}