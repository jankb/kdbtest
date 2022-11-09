package net.polvott.web

import dto.Sample
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.SampleRouter()
{
    route("/sample")
    {
        get("/{id}")
        {
            call.respondText("This is some sample data for sample ${call.parameters["id"]}", ContentType.Text.Html)
        }

        post("/create") {
            val data = call.receive<Sample>()
            println(data)
            call.respond(HttpStatusCode.Created, "Created sample for ID ${data.id}")
        }
    }
}