package web

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.http.*
import io.ktor.server.application.*

fun Route.setupStatics()
{
  get("/")
  {
    val indexPage = javaClass.getResource("/index.html").readText()
    call.respondText(indexPage, ContentType.Text.Html)
  }
}
