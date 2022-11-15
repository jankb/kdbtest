package web // ktlint-disable filename

import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.setupStatics() {
    get("/") {
        val indexPage = javaClass.getResource("/index.html").readText()
        call.respondText(indexPage, ContentType.Text.Html)
    }
}
