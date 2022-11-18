package web // ktlint-disable filename


import io.ktor.http.ContentType
import io.ktor.http.content.EntityTagVersion
import io.ktor.http.content.LastModifiedVersion
import io.ktor.server.application.call
import io.ktor.server.application.hooks.CallFailed.install
import io.ktor.server.application.install
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

import io.ktor.server.plugins.conditionalheaders.*
import java.io.File
import java.util.*

fun Route.setupStatics() {

    val landingPage = "/index.html"

    install(ConditionalHeaders)
    {
        val file = File(javaClass.getResource(landingPage).file)
        version { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.Html -> listOf(
                    EntityTagVersion(file.lastModified().hashCode().toString()),
                    LastModifiedVersion(Date(file.lastModified()))
                )
                else -> emptyList()
            }
        }
    }

    get("/") {
        val indexPage = javaClass.getResource(landingPage)
        call.respondText(indexPage.readText(), ContentType.Text.Html)
    }
}
