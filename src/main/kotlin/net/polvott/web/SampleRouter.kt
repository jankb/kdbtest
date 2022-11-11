package net.polvott.web

import net.polvott.dto.Sample
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

import net.polvott.dto.Samples


fun Route.sampleRouter(db: net.polvott.database.Database) {
    route("/samples")
    {
        get("/{id}")
        {
            call.respondText("This is some sample data for sample ${call.parameters["id"]}", ContentType.Text.Html)

            val id = call.parameters["id"]?.toInt()

            db.get(id)


        }

        post {

            val data = call.receive<Sample>()
            println(data)

            db.insert(data.name)

            call.respond(HttpStatusCode.Created, "Created sample for ID ${data.id}")
        }

        get {
            call.respondText("All Data", ContentType.Text.Plain)
        }
    }
}
