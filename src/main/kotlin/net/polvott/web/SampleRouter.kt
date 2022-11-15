package net.polvott.web

import net.polvott.dto.Sample
import net.polvott.database.dao
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
import io.ktor.server.util.getOrFail


fun Route.sampleRouter() {
    route("/samples")
    {
        get("/{id}")
        {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val sample = dao.sample(id)
            if (sample == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(sample)

        }

        post {

            val data = call.receive<Sample>()
            println(data)
            call.respond(HttpStatusCode.Created, "Created sample for ID ${data.sample_id}")
        }

        get {
            val samples = dao.allSamples()
            if (samples == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(samples)

            call.respondText("All Data", ContentType.Text.Plain)
        }
    }
}
