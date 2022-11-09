package web // ktlint-disable filename

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.setupStatics() {
    get("/") {
        val indexPage = javaClass.getResource("/index.html").readText()
        call.respondText(indexPage, ContentType.Text.Html)
    }
}
